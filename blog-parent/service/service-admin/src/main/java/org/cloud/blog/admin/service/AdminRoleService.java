package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.domain.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
public interface AdminRoleService extends IService<AdminRole> {

    List<Role> findUserHasRoles(Long id);

    void saveHasRoles(List<Integer> hasRoles, Long adminId);
}
