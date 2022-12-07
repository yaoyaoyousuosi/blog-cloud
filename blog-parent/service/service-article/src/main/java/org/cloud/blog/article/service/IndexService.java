package org.cloud.blog.article.service;

import org.cloud.blog.article.domain.Article;
import org.cloud.blog.article.domain.Category;
import org.cloud.blog.article.domain.Tag;
import org.cloud.blog.article.vo.ArchivesVo;
import org.cloud.blog.article.vo.BodyVo;
import org.cloud.blog.common.utils.PageVo;

import java.util.List;

public interface IndexService {
    List<Article> queryArticles(PageVo vo);

    Article articleView(String id);

    List<Tag> hotTags();

    List<Article> articleHot();

    List<Article> articleNews();

    List<ArchivesVo> articleListArchives();

    List<BodyVo> articleSearch(String keyword);

    List<Category> categoryDetails();

    List<Tag> tagDetails();

    Category categoryDetailById(String id);

    Tag tagDetailById(String id);
}
