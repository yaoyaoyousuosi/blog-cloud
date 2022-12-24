package org.cloud.blog.admin.controller;
import org.cloud.blog.admin.service.SysUserService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.common.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/front/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/list")
    public Response list(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = sysUserService.sysUserList(pageUtil);
        return Response.ok(data);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable String id){
        Boolean bool = sysUserService.deleteSysUserById(id);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

}
