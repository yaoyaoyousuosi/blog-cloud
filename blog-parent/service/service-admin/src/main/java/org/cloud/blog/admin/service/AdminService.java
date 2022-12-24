package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.util.PageUtil;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-21
 */
public interface AdminService extends IService<Admin> {

    Map<String, Object> userList(PageUtil pageUtil);

    Boolean add(Admin admin);

    Boolean updateAdminWithHasRoles(Admin admin);

    Boolean updateAdminEnableStatus(Integer id);
}
