package org.cloud.blog.article.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.blog.article.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.blog.article.domain.Tag;
import org.cloud.blog.article.vo.ArchivesVo;
import org.cloud.blog.common.utils.PageVo;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> queryArticles(@Param("vo") PageVo vo, @Param("ids") List<String> ids);

    List<Tag> queryArticlesTags(String id);

    Article articleView(String id);

    List<ArchivesVo> articleListArchives();

    List<String> selectArticleIdByTagId(String tagId);
}
