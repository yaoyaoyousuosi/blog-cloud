package org.cloud.blog.ucenter.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.cloud.blog.common.exception.BlogException;
import org.cloud.blog.common.utils.Response;

import org.cloud.blog.ucenter.domain.SysUser;
import org.cloud.blog.ucenter.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whj
 * @since 2022-11-28
 */
@RestController
@RequestMapping("/ucenter/sysUser")
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    @PostMapping("/login")
    @SentinelResource(value = "ucenter.admin.login", fallback = "userLoginFallBackError",
            blockHandler = "userLoginFallBackBlock")
    public Response userLogin(@RequestBody SysUser user) throws BlogException {
        String token = sysUserService.userLogin(user);
        return Response.ok(token).setMsg(null);
    }

    public Response userLoginFallBackError(SysUser admin, Throwable throwable){
        Response error = Response.error();
        error.setMsg(throwable.getMessage());
        return error;
    }

    public Response userLoginFallBackBlock(SysUser admin, BlockException throwable){
        Response error = Response.error();
        error.setMsg("点击频率过快，稍后再试");
        return error;
    }


    @PostMapping("/register")
    public Response doRegister(@RequestBody SysUser sysUser) throws BlogException {
        String token = sysUserService.doRegister(sysUser);
        if(null == token){
            Response error = Response.error();
            error.setMsg("该用户已经注册过");
            return error;
        }
        return Response.ok(token).setMsg(null);
    }

    @GetMapping("/currentUser")
    public Response getCurrentUser(@RequestHeader("Oauth-Token") String token) {
        SysUser sysUser = sysUserService.getCurrentUser(token);
        return Response.ok(sysUser).setMsg(null);
    }

    @PostMapping("/update")
    public Response updateUser(@RequestBody SysUser user, @RequestHeader("authId") String authId) {
        sysUserService.updateUser(user, authId);
        return Response.ok(null).setMsg("更新成功");
    }
}
