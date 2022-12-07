package org.cloud.blog.article.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.blog.article.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.blog.article.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> hotTags();

    void insertArticleTag(@Param("articleId") String articleId,
                          @Param("tagId") String tagId,
                          @Param("id") String id);
}
