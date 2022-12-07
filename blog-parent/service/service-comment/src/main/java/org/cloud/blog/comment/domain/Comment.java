package org.cloud.blog.comment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document
public class Comment {
    @Id
    private String id;
    private String articleId;
    private String author;
    private String avatar;
    private String content;
    private Date createDate;
    private List levelTwoComment;
}
