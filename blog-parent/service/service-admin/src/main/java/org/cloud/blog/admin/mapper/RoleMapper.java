package org.cloud.blog.admin.mapper;

import org.cloud.blog.admin.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.blog.admin.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleVo> findRoleList();
}
