package org.cloud.blog.admin.mapper;

import org.cloud.blog.admin.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.blog.admin.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<PermissionVo> findPermissionList();
}
