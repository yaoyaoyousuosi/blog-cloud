package org.cloud.blog.admin.mapper;

import org.cloud.blog.admin.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.ArticleVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleVo> list(PageUtil pageUtil);

    void deleteArticleTag(String id);

    void deleteArticleBody(String id);
}
