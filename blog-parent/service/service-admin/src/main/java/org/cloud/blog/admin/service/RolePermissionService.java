package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
public interface RolePermissionService extends IService<RolePermission> {

    void saveRoleHasPermission(List<Integer> permissionIds, Long id);
}
