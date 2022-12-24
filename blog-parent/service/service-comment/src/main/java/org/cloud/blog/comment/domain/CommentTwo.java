package org.cloud.blog.comment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
