package org.cloud.blog.ucenter.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.cloud.blog.common.exception.BlogException;
import org.cloud.blog.common.utils.JwtUtils;
import org.cloud.blog.common.utils.RsaUtil;
import org.cloud.blog.ucenter.domain.Admin;
import org.cloud.blog.ucenter.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.util.StringUtils;

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
}
