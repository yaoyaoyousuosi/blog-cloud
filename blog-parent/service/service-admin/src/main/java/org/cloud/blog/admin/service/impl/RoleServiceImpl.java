package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.domain.Permission;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.domain.RolePermission;
import org.cloud.blog.admin.mapper.RoleMapper;
import org.cloud.blog.admin.service.PermissionService;
import org.cloud.blog.admin.service.RolePermissionService;
import org.cloud.blog.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.RoleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource private PermissionService permissionService;
    @Resource private RolePermissionService rolePermissionService;

    @Override
    public List<RoleVo> findRoleList() {
        return baseMapper.findRoleList();
    }

    @Override
    public Map<String, Object> roleList(PageUtil pageUtil) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            queryWrapper.like(Role::getNameZh, pageUtil.getQueryString());
        }
        Page<Role> page = new Page<>(pageUtil.getCurrentPage(), pageUtil.getPageSize());
        baseMapper.selectPage(page, queryWrapper);
        List<Role> roles = page.getRecords();
        setRoleHasPermission(roles);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", roles);
        data.put("total", page.getTotal());
        return data;
    }

    private void setRoleHasPermission(List<Role> roles){
        for (Role role : roles){
            LambdaQueryWrapper<RolePermission> rolePermissionWrapper = new LambdaQueryWrapper<>();
            rolePermissionWrapper.eq(RolePermission::getRoleId, role.getId());
            List<RolePermission> rolePermissions = rolePermissionService.list(rolePermissionWrapper);
            ArrayList<Permission> permissions = new ArrayList<>();
            for (RolePermission rolePermission : rolePermissions){
                Permission permission = permissionService.getById(rolePermission.getPermissionId());
                permissions.add(permission);
            }
            role.setHasPermission(permissions);
        }
    }


    @Override
    public Boolean updateRoleEnableStatus(Integer id) {
        Role role = baseMapper.selectById(id);
        if(role.getDeleted()){
            role.setDeleted(false);
            baseMapper.updateById(role);
            return true;
        }
        role.setDeleted(true);
        baseMapper.updateById(role);
        return true;
    }

    @Transactional
    @Override
    public Boolean add(Role role) {
        String name = role.getName();
        if(!name.startsWith("ROLE_")){
            role.setName("ROLE_" + name);
        }
        baseMapper.insert(role);
        rolePermissionService.saveRoleHasPermission(role.getPermissionIds(), role.getId());
        return true;
    }

    @Transactional
    @Override
    public Boolean updateRoleWithHasPermission(Role role) {
        String name = role.getName();
        if(!name.startsWith("ROLE_")){
            role.setName("ROLE_" + name);
        }
        baseMapper.updateById(role);
        LambdaQueryWrapper<RolePermission> rolePermissionWrapper = new LambdaQueryWrapper<>();
        rolePermissionWrapper.eq(RolePermission::getRoleId, role.getId());
        rolePermissionService.remove(rolePermissionWrapper);
        rolePermissionService.saveRoleHasPermission(role.getPermissionIds(), role.getId());
        return true;
    }
}
