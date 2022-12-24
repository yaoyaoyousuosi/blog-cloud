package org.cloud.blog.admin.controller;

import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.service.RoleService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.RoleVo;
import org.cloud.blog.common.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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
@RequestMapping("/admin/role")
public class RoleController {

    @Resource private RoleService roleService;

    @GetMapping("/roleList")
    public Response findRoleList(){
        List<RoleVo> roleVoList = roleService.findRoleList();
        return Response.ok(roleVoList);
    }


    @PostMapping("/list")
    public Response list(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = roleService.roleList(pageUtil);
        return Response.ok(data);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id){
        Boolean bool = roleService.updateRoleEnableStatus(id);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

    @PostMapping("/add")
    public Response add(@RequestBody Role role){
        Boolean bool = roleService.add(role);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

    @PostMapping("/update")
    public Response update(@RequestBody Role role){
        Boolean bool = roleService.updateRoleWithHasPermission(role);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }
}
