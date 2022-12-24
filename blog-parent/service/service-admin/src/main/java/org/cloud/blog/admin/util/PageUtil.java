package org.cloud.blog.admin.util;

import lombok.Data;

@Data
public class PageUtil {
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private String queryString;
    private Integer total = 0;
}
