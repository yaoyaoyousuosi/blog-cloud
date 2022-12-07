package org.cloud.blog.article.service;

import org.cloud.blog.article.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.article.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
public interface TagService extends IService<Tag> {

    List<Tag> hotTags();

    List<Tag> tagDetails();

    Tag tagDetailById(String id);

    void insertArticleTag(String articleId, List<TagVo> tagIds);
}
