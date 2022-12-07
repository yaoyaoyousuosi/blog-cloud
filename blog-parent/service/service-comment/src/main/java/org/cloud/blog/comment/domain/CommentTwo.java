package org.cloud.blog.comment.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CommentTwo {
    private String _id;
    private String parent;
    private String content;
    private String fromUser;
    private String toUser;
    private String avatar;
    private String articleId;
    private Date createDate;
}
