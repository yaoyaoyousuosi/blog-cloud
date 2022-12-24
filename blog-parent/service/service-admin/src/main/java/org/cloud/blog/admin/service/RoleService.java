package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.RoleVo;

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
public interface RoleService extends IService<Role> {

    List<RoleVo> findRoleList();

    Map<String, Object> roleList(PageUtil pageUtil);

    Boolean updateRoleEnableStatus(Integer id);

    Boolean add(Role role);

    Boolean updateRoleWithHasPermission(Role role);
}
