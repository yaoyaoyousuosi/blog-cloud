package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.domain.AdminRole;
import org.cloud.blog.admin.domain.Permission;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.mapper.AdminMapper;
import org.cloud.blog.admin.service.AdminRoleService;
import org.cloud.blog.admin.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.util.PageUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource private BCryptPasswordEncoder passwordEncoder;
    @Resource private AdminRoleService adminRoleService;

    @Override
    public Map<String, Object> userList(PageUtil pageUtil) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Admin::getUsername, Admin::getId, Admin::getDeleted);
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            queryWrapper.like(Admin::getUsername, pageUtil.getQueryString());
        }
        Page<Admin> page = new Page<>(pageUtil.getCurrentPage(), pageUtil.getPageSize());
        baseMapper.selectPage(page, queryWrapper);
        List<Admin> admins = page.getRecords();
        for (Admin admin : admins){
            List<Role> roles = adminRoleService.findUserHasRoles(admin.getId());
            admin.setRoles(roles);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", admins);
        data.put("total", page.getTotal());
        return data;
    }


    @Transactional
    @Override
    public Boolean add(Admin admin) {
        String encodePwd = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePwd);
        baseMapper.insert(admin);
        adminRoleService.saveHasRoles(admin.getHasRoles(), admin.getId());
        return true;
    }

    @Transactional
    @Override
    public Boolean updateAdminWithHasRoles(Admin admin) {
        baseMapper.updateById(admin);
        LambdaQueryWrapper<AdminRole> adminRoleWrapper = new LambdaQueryWrapper<>();
        adminRoleWrapper.eq(AdminRole::getAdminId, admin.getId());
        adminRoleService.remove(adminRoleWrapper);
        adminRoleService.saveHasRoles(admin.getHasRoles(), admin.getId());
        return true;
    }


    @Override
    public Boolean updateAdminEnableStatus(Integer id) {
        Admin admin = baseMapper.selectById(id);
        if(admin.getDeleted()){
            admin.setDeleted(false);
            baseMapper.updateById(admin);
            return true;
        }
        admin.setDeleted(true);
        baseMapper.updateById(admin);
        return true;
    }
}
