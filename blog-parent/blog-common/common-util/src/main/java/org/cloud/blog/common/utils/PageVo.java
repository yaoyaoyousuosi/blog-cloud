package org.cloud.blog.common.utils;

import lombok.Data;

@Data
public class PageVo {
    private Integer page = 1;
    private Integer pageSize = 5;
    private String categoryId;
    private String tagId;
    private String year;
    private String month;
}
