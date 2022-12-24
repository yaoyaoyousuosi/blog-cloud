package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.blog.admin.domain.Permission;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.domain.RolePermission;
import org.cloud.blog.admin.mapper.PermissionMapper;
import org.cloud.blog.admin.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.service.RolePermissionService;
import org.cloud.blog.admin.service.RoleService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.PermissionVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource private RolePermissionService rolePermissionService;
    @Resource private RoleService roleService;

    @Override
    public Map<String, Object> permissionList(PageUtil pageUtil) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            queryWrapper.like(Permission::getName, pageUtil.getQueryString());
        }
        Page<Permission> page = new Page<>(pageUtil.getCurrentPage(), pageUtil.getPageSize());
        baseMapper.selectPage(page, queryWrapper);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }


    @Override
    public boolean deleteById(Long id) {
        Permission permission = baseMapper.selectById(id);
        if(permission.getDeleted()){
            permission.setDeleted(false);
            baseMapper.updateById(permission);
            return true;
        }
        permission.setDeleted(true);
        baseMapper.updateById(permission);
        return true;
    }

    @Override
    public boolean add(Permission permission) {
        int count = baseMapper.insert(permission);
        if(count > 0){
            return true;
        }
        return false;
    }


    @Override
    public List<PermissionVo> findPermissionList() {
        return baseMapper.findPermissionList();
    }

    @Override
    public List<Permission> permissionWithRole() {
        LambdaQueryWrapper<Permission> permissionWrapper = new LambdaQueryWrapper<>();
        List<Permission> permissions = baseMapper.selectList(permissionWrapper);
        setPermissionHasRole(permissions);
        return permissions;
    }

    private void setPermissionHasRole(List<Permission> permissions) {
        for (Permission permission : permissions){
            LambdaQueryWrapper<RolePermission> rolePermissionWrapper = new LambdaQueryWrapper<>();
            rolePermissionWrapper.eq(RolePermission::getPermissionId, permission.getId());
            List<RolePermission> rolePermissions = rolePermissionService.list(rolePermissionWrapper);
            ArrayList<Role> roles = new ArrayList<>();
            for (RolePermission rolePermission : rolePermissions){
                Role role = roleService.getById(rolePermission.getRoleId());
                roles.add(role);
            }
            permission.setRoles(roles);
        }
    }
}
