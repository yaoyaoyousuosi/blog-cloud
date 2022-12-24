package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.util.PageUtil;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
public interface ArticleService extends IService<Article> {

    Map<String, Object> articleList(PageUtil pageUtil);

    Boolean deleteArticleById(String id);
}
