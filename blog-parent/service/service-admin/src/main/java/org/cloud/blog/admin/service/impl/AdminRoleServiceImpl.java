package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.cloud.blog.admin.domain.AdminRole;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.mapper.AdminRoleMapper;
import org.cloud.blog.admin.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
    @Resource private RoleService roleService;

    @Override
    public List<Role> findUserHasRoles(Long id) {
        LambdaQueryWrapper<AdminRole> adminRoleQueryWrapper = new LambdaQueryWrapper<>();
        adminRoleQueryWrapper.eq(AdminRole::getAdminId, id);
        List<AdminRole> adminRoles = baseMapper.selectList(adminRoleQueryWrapper);
        ArrayList<Role> roles = new ArrayList<>();
        for (AdminRole adminRole : adminRoles){
            LambdaQueryWrapper<Role> roleQueryWrapper = new LambdaQueryWrapper<>();
            roleQueryWrapper.eq(Role::getId, adminRole.getRoleId());
            roleQueryWrapper.eq(Role::getDeleted, false);
            roles.add(roleService.getOne(roleQueryWrapper));
        }
        return roles;
    }

    @Override
    public void saveHasRoles(List<Integer> hasRoles, Long adminId) {
        for (Integer role : hasRoles){
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(role);
            baseMapper.insert(adminRole);
        }
    }
}
