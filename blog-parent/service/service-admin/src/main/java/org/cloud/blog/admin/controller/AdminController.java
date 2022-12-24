package org.cloud.blog.admin.controller;

import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.service.AdminService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.common.utils.Response;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
@RestController
@RequestMapping("/admin/user")
public class AdminController {

    @Resource private AdminService adminService;

    @PostMapping("/userInfo")
    public String getUserInfo(Authentication authentication){
        return ((Admin) authentication.getPrincipal()).getUsername();
    }

    @PostMapping("/list")
    public Response list(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = adminService.userList(pageUtil);
        return Response.ok(data);
    }

    @PostMapping("/add")
    public Response add(@RequestBody Admin admin){
        Boolean bool = adminService.add(admin);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

    @PostMapping("/update")
    public Response update(@RequestBody Admin admin){
        Boolean bool = adminService.updateAdminWithHasRoles(admin);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id){
        Boolean bool = adminService.updateAdminEnableStatus(id);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }
}
