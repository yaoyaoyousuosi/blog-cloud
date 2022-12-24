package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.util.PageUtil;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
public interface CategoryService extends IService<Category> {

    Map<String, Object> categoryList(PageUtil pageUtil);

    Boolean deleteCategoryById(Integer id);

    Boolean add(Category category);

    Boolean updateCategory(Category category);
}
