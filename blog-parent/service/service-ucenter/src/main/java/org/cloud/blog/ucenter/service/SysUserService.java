package org.cloud.blog.ucenter.service;

import org.cloud.blog.common.exception.BlogException;
import org.cloud.blog.ucenter.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-11-28
 */
public interface SysUserService extends IService<SysUser> {
    String userLogin(SysUser user) throws BlogException;

    String doRegister(SysUser sysUser) throws BlogException;

    SysUser getCurrentUser(String token);

    void updateUser(SysUser user, String userId);
}
