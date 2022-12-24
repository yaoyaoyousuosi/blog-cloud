package org.cloud.blog.comment.dao;

import org.cloud.blog.comment.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment, String> {

    List<Comment> findCommentsByArticleIdOrderByCreateDateDesc(String articleId);

    Long deleteByArticleId(String articleId);

    Long countCommentsByArticleId(String articleId);
}
