package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.domain.AdminRole;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.service.AdminRoleService;
import org.cloud.blog.admin.service.AdminService;
import org.cloud.blog.admin.service.RoleService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource private AdminService adminService;
    @Resource private AdminRoleService adminRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Admin> adminQueryWrapper = new LambdaQueryWrapper<>();
        adminQueryWrapper.eq(Admin::getUsername, username);
        Admin admin = adminService.getOne(adminQueryWrapper);
        if(null == admin){
            throw new UsernameNotFoundException("用户不存在");
        }
        admin.setRoles(adminRoleService.findUserHasRoles(admin.getId()));
        return admin;
    }
}
