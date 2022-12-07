package org.cloud.blog.article.service;

import org.cloud.blog.article.domain.ArticleBody;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
public interface ArticleBodyService extends IService<ArticleBody> {

    String insertArticleBody(Map<String, String> body);
}
