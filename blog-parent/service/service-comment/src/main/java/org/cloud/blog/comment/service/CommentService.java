package org.cloud.blog.comment.service;

import org.cloud.blog.comment.domain.Comment;
import org.cloud.blog.comment.domain.CommentTwo;

import java.util.List;
import java.util.Map;

public interface CommentService {

    Comment createComment(Comment comment);

    List<Comment> queryCommentsById(String articleId);

    CommentTwo createCommentTwo(CommentTwo reply);
}
