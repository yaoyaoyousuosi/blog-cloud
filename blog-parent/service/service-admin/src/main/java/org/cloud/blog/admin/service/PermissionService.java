package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.PermissionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
public interface PermissionService extends IService<Permission> {

    Map<String, Object> permissionList(PageUtil pageUtil);

    boolean deleteById(Long id);

    boolean add(Permission permission);

    List<PermissionVo> findPermissionList();

    List<Permission> permissionWithRole();
}
