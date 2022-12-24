package org.cloud.blog.admin.service.impl;

import org.cloud.blog.admin.domain.RolePermission;
import org.cloud.blog.admin.mapper.RolePermissionMapper;
import org.cloud.blog.admin.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public void saveRoleHasPermission(List<Integer> permissionIds, Long id) {
        for (Integer pid : permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(pid);
            rolePermission.setRoleId(id);
            baseMapper.insert(rolePermission);
        }
    }
}
