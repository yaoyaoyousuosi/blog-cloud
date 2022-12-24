package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.domain.Role;
import org.cloud.blog.admin.domain.Tag;
import org.cloud.blog.admin.mapper.TagMapper;
import org.cloud.blog.admin.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public Map<String, Object> tagList(PageUtil pageUtil) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            queryWrapper.like(Tag::getTagName, pageUtil.getQueryString());
        }
        Page<Tag> page = new Page<>(pageUtil.getCurrentPage(), pageUtil.getPageSize());
        baseMapper.selectPage(page, queryWrapper);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }


    @Transactional
    @Override
    public Boolean deleteTagById(Integer id) {
        baseMapper.deleteById(id);
        baseMapper.deleteTagIdFormTagArticle(id);
        return true;
    }

    @Override
    public Boolean add(Tag tag) {
        baseMapper.insert(tag);
        return true;
    }

    @Override
    public Boolean updateTag(Tag tag) {
        baseMapper.updateById(tag);
        return true;
    }
}
