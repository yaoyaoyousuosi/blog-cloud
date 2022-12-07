package org.cloud.blog.article.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "canal_article")
public class BodyVo implements Serializable {
    @Id
    private String id;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Keyword, index = false)
    private String summary;
}
