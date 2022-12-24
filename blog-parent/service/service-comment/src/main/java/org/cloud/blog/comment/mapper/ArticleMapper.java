package org.cloud.blog.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    void updateCommentCounts(@Param("id") String articleId, @Param("commentCounts") Long commentCounts);
}
