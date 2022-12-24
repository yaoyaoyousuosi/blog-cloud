package org.cloud.blog.admin.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.cloud.blog.admin.domain.Article;
import org.cloud.blog.admin.domain.SysUser;
import org.cloud.blog.admin.mapper.SysUserMapper;
import org.cloud.blog.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.util.PageUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public Map<String, Object> sysUserList(PageUtil pageUtil) {
        Long total = null;
        LambdaQueryWrapper<SysUser> sysUserWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            sysUserWrapper.like(SysUser::getNickname, pageUtil.getQueryString());
        }
        total = baseMapper.selectCount(sysUserWrapper);
        pageUtil.setCurrentPage((pageUtil.getCurrentPage() - 1) * pageUtil.getPageSize());
        List<SysUser> sysUserList = baseMapper.list(pageUtil);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", sysUserList);
        data.put("total", total);
        return data;
    }

    @Override
    public Boolean deleteSysUserById(String id) {
        LambdaQueryWrapper<SysUser> sysUserWrapper = new LambdaQueryWrapper<>();
        sysUserWrapper.eq(SysUser::getId, id);
        sysUserWrapper.select(SysUser::getDeleted, SysUser::getId);
        SysUser sysUser = baseMapper.selectOne(sysUserWrapper);
        if(sysUser.getDeleted()){
            sysUser.setDeleted(false);
        }else{
            sysUser.setDeleted(true);
        }
        baseMapper.updateById(sysUser);
        return true;
    }
}
