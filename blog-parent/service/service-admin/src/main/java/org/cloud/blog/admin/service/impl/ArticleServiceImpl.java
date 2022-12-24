package org.cloud.blog.admin.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.cloud.blog.admin.client.CommentClient;
import org.cloud.blog.admin.domain.Article;
import org.cloud.blog.admin.domain.SysUser;
import org.cloud.blog.admin.mapper.ArticleMapper;
import org.cloud.blog.admin.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.admin.util.PageUtil;
import org.cloud.blog.admin.vo.ArticleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource private CommentClient commentClient;

    @Override
    public Map<String, Object> articleList(PageUtil pageUtil) {
        Long total = null;
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(pageUtil.getQueryString())){
            articleWrapper.like(Article::getTitle, pageUtil.getQueryString());
        }
        total = baseMapper.selectCount(articleWrapper);
        pageUtil.setCurrentPage((pageUtil.getCurrentPage() - 1) * pageUtil.getPageSize());
        List<ArticleVo> articleVos = baseMapper.list(pageUtil);
        HashMap<String, Object> data = new HashMap<>();
        data.put("list", articleVos);
        data.put("total", total);
        return data;
    }


    @Transactional
    @Override
    public Boolean deleteArticleById(String id) {
        baseMapper.deleteById(id);
        baseMapper.deleteArticleTag(id);
        baseMapper.deleteArticleBody(id);
        commentClient.deleteCommentById(id);
        return true;
    }
}
