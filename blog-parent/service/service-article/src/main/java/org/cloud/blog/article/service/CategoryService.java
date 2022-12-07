package org.cloud.blog.article.service;

import org.cloud.blog.article.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
public interface CategoryService extends IService<Category> {

    List<Category> categoryDetails();

    Category categoryDetailById(String id);
}
