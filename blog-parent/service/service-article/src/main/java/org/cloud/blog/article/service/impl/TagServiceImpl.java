package org.cloud.blog.article.service.impl;

import cn.hutool.core.util.IdUtil;
import org.cloud.blog.article.domain.Tag;
import org.cloud.blog.article.mapper.TagMapper;
import org.cloud.blog.article.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.article.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public List<Tag> hotTags() {
        List<Tag> tags = baseMapper.hotTags();
        return tags;
    }

    @Override
    public List<Tag> tagDetails() {
        return baseMapper.selectList(null);
    }

    @Override
    public Tag tagDetailById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void insertArticleTag(String articleId, List<TagVo> tagVos) {
        for (TagVo vo : tagVos){
            String id = String.valueOf(IdUtil.getSnowflakeNextId());
            baseMapper.insertArticleTag(articleId, vo.getId(), id);
        }

    }
}
