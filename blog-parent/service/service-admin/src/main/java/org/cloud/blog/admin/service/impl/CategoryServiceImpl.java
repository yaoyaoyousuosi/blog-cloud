package org.cloud.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.blog.admin.domain.Article;
import org.cloud.blog.admin.domain.Category;
import org.cloud.blog.admin.domain.Tag;
import org.cloud.blog.admin.mapper.CategoryMapper;
import org.cloud.blog.admin.service.ArticleService;
import org.cloud.blog.admin.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource private ArticleService articleService;

    @Override
    public Map<String, Object> categoryList(PageUtil pageUtil) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            queryWrapper.like(Category::getCategoryName, pageUtil.getQueryString());
        }
        Page<Category> page = new Page<>(pageUtil.getCurrentPage(), pageUtil.getPageSize());
        baseMapper.selectPage(page, queryWrapper);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", page.getRecords());
        data.put("total", page.getTotal());
        return data;
    }

    @Transactional
    @Override
    public Boolean deleteCategoryById(Integer id) {
        baseMapper.deleteById(id);
        LambdaUpdateWrapper<Article> articleUpdateWrapper = new LambdaUpdateWrapper<>();
        articleUpdateWrapper.set(Article::getCategoryId, null);
        articleUpdateWrapper.eq(Article::getCategoryId, id);
        articleService.update(articleUpdateWrapper);
        return true;
    }

    @Override
    public Boolean add(Category category) {
        baseMapper.insert(category);
        return true;
    }

    @Override
    public Boolean updateCategory(Category category) {
        baseMapper.updateById(category);
        return true;
    }
}
