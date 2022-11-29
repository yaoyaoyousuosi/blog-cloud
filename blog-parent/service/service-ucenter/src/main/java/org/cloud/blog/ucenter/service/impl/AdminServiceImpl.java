package org.cloud.blog.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.cloud.blog.common.exception.BlogException;
import org.cloud.blog.common.utils.JwtUtils;
import org.cloud.blog.common.utils.RsaUtil;
import org.cloud.blog.ucenter.domain.Admin;
import org.cloud.blog.ucenter.mapper.AdminMapper;
import org.cloud.blog.ucenter.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-11-28
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;


}
