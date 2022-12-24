package org.cloud.blog.admin.mapper;

import org.cloud.blog.admin.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.blog.admin.util.PageUtil;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> list(PageUtil pageUtil);
}
