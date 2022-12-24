package org.cloud.blog.article.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cloud.blog.article.domain.Article;
import org.cloud.blog.article.domain.ArticleBody;
import org.cloud.blog.article.domain.Tag;
import org.cloud.blog.article.mapper.ArticleMapper;
import org.cloud.blog.article.service.ArticleBodyService;
import org.cloud.blog.article.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cloud.blog.article.service.TagService;
import org.cloud.blog.article.vo.ArchivesVo;
import org.cloud.blog.article.vo.ArticleVo;
import org.cloud.blog.article.vo.TagVo;
import org.cloud.blog.common.utils.PageVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource private ArticleBodyService articleBodyService;
    @Resource private TagService tagService;
    @Resource private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Article> queryArticles(PageVo vo) {
        vo.setPage((vo.getPage() - 1) * vo.getPageSize());
        List<String> ids = null;
        if(!StringUtils.isEmpty(vo.getTagId())){
            ids = baseMapper.selectArticleIdByTagId(vo.getTagId());
            if(ids.size() == 0){
                return null;
            }
        }
        List<Article> articles = baseMapper.queryArticles(vo, ids);
        return setValue(articles);
    }

    private List<Article> setValue(List<Article> articles){
        for (Article article : articles){
            List<Tag> tags = baseMapper.queryArticlesTags(article.getId());
            article.setTags(tags);
        }
        return articles;
    }

    private Article setValue(Article article){
        List<Tag> tags = baseMapper.queryArticlesTags(article.getId());
        article.setTags(tags);
        return article;
    }

    @Override
    public List<Article> articleHot() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getViewCounts).last("limit 0, 4");
        wrapper.select(Article::getId, Article::getTitle);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Article> articleNews() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getCreateDate).last("limit 0, 4");
        wrapper.select(Article::getId, Article::getTitle);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<ArchivesVo> articleListArchives() {
        return baseMapper.articleListArchives();
    }

    @Transactional
    @Override
    public String articlePublish(ArticleVo vo, String authId) {
        String articleId = insertArticle(vo, authId);
        String articleBodyId = insertArticleBody(vo.getBody(), vo.getSummary(), articleId);
        insertArticleTag(articleId, vo.getTags());
        Article article = new Article();
        article.setBodyId(articleBodyId);
        LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Article::getId, articleId);
        baseMapper.update(article, wrapper);
        return articleId;
    }

    private String insertArticle(ArticleVo vo, String authId){
        Article article = new Article();
        article.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        article.setAuthorId(authId);
        article.setCommentCounts(0);
        article.setSummary(vo.getSummary());
        article.setTitle(vo.getTitle());
        article.setWeight(0);
        article.setCategoryId(vo.getCategory().get("id"));
        article.setCreateDate(String.valueOf(System.currentTimeMillis()));
        article.setViewCounts("0");
        baseMapper.insert(article);
        return article.getId();
    }

    private String insertArticleBody(Map<String, String> body, String summary, String articleId){
        body.put("summary", summary);
        body.put("articleId", articleId);
        return articleBodyService.insertArticleBody(body);
    }

    private void insertArticleTag(String articleId, List<TagVo> tagIds){
        tagService.insertArticleTag(articleId, tagIds);
    }

    @Override
    public Article articleView(String id) {
        Article article = baseMapper.articleView(id);
        Boolean hasKey = stringRedisTemplate.hasKey("view-" + id);
        if(hasKey){
            Long viewCount = stringRedisTemplate.opsForValue().increment("view-" + id);
            article.setViewCounts(String.valueOf(viewCount));
        }else {
            stringRedisTemplate.opsForValue().set("view-" + id, article.getViewCounts());
        }
        return setValue(article);
    }

    @Override
    public void updateViews() {
        Set<String> keys = stringRedisTemplate.keys("view-*");
        for (String key : keys) {
            Long viewCounts = Long.valueOf(stringRedisTemplate.opsForValue().get(key));
            key = key.substring(key.lastIndexOf("-") + 1);
            Article article = new Article();
            article.setId(key);
            article.setViewCounts(String.valueOf(viewCounts));
            baseMapper.updateById(article);
            stringRedisTemplate.delete("view-" + key);
        }
    }
}
