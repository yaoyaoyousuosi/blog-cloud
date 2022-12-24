package org.cloud.blog.admin.controller;

import org.cloud.blog.admin.domain.Permission;
import org.cloud.blog.admin.service.PermissionService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.PermissionVo;
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
@RequestMapping("/admin/permission")
public class PermissionController {

    @Resource private PermissionService permissionService;

    @PostMapping("/permissionList")
    public Response permissionList(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = permissionService.permissionList(pageUtil);
        return Response.ok(data);
    }

    @PostMapping("/update")
    public Response update(@RequestBody Permission permission){
        boolean bool = permissionService.updateById(permission);
        if(bool){
            return Response.ok(null);
        }
        return Response.error();
    }

    @GetMapping("/delete/{id}")
    public Response deleteById(@PathVariable Long id){
        boolean bool = permissionService.deleteById(id);
        if(bool){
            return Response.ok(null);
        }
        return Response.error();
    }

    @PostMapping("add")
    public Response add(@RequestBody Permission permission){
        boolean bool = permissionService.add(permission);
        if(bool){
            return Response.ok(null);
        }
        return Response.error();
    }

    @GetMapping("/list")
    public Response findRoleList(){
        List<PermissionVo> permissionVos = permissionService.findPermissionList();
        return Response.ok(permissionVos);
    }

}
