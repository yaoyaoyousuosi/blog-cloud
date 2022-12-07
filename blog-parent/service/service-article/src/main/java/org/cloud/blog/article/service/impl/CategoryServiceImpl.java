package org.cloud.blog.article.service.impl;

import org.cloud.blog.article.domain.Category;
import org.cloud.blog.article.mapper.CategoryMapper;
import org.cloud.blog.article.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> categoryDetails() {
        return baseMapper.selectList(null);
    }

    @Override
    public Category categoryDetailById(String id) {
        return baseMapper.selectById(id);
    }
}
