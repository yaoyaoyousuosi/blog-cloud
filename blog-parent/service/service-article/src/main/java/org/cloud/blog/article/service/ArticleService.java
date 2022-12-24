package org.cloud.blog.article.service;

import org.cloud.blog.article.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.article.vo.ArchivesVo;
import org.cloud.blog.article.vo.ArticleVo;
import org.cloud.blog.common.utils.PageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
public interface ArticleService extends IService<Article> {

    List<Article> queryArticles(PageVo vo);

    Article articleView(String id);

    List<Article> articleHot();

    List<Article> articleNews();

    List<ArchivesVo> articleListArchives();

    String articlePublish(ArticleVo vo, String authId);

    void updateViews();
}
