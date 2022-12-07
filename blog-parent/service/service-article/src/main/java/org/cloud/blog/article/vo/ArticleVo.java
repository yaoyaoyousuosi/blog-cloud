package org.cloud.blog.article.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ArticleVo {
    private String title;
    private String summary;
    private Map<String, String> category;
    private Map<String, String> body;
    private List<TagVo> tags;
}
