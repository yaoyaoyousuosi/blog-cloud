package org.cloud.blog.article.service.impl;

import org.cloud.blog.article.domain.Article;
import org.cloud.blog.article.domain.Category;
import org.cloud.blog.article.domain.Tag;
import org.cloud.blog.article.service.ArticleService;
import org.cloud.blog.article.service.CategoryService;
import org.cloud.blog.article.service.IndexService;
import org.cloud.blog.article.service.TagService;
import org.cloud.blog.article.vo.ArchivesVo;
import org.cloud.blog.article.vo.BodyVo;
import org.cloud.blog.common.utils.PageVo;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource private ArticleService articleService;
    @Resource private TagService tagService;
    @Resource private CategoryService categoryService;
    @Resource private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<Article> queryArticles(PageVo vo) {
        return articleService.queryArticles(vo);
    }

    @Override
    public Article articleView(String id) {
        return articleService.articleView(id);
    }

    @Override
    public List<Tag> hotTags() {
        return tagService.hotTags();
    }

    @Override
    public List<Article> articleHot() {
        return articleService.articleHot();
    }

    @Override
    public List<Article> articleNews() {
        return articleService.articleNews();
    }

    @Override
    public List<ArchivesVo> articleListArchives() {
        return articleService.articleListArchives();
    }

    @Override
    public List<BodyVo> articleSearch(String keyword) {
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchQuery("content", keyword));
        SourceFilter filter = new FetchSourceFilter(new String[]{"_id", "summary"}, null);
        query.addSourceFilter(filter);
        List<BodyVo> vos = elasticsearchRestTemplate.queryForList(query, BodyVo.class, IndexCoordinates.of("canal_article"));
        return vos;
    }

    @Override
    public List<Category> categoryDetails() {
        return categoryService.categoryDetails();
    }

    @Override
    public List<Tag> tagDetails() {
        return tagService.tagDetails();
    }

    @Override
    public Category categoryDetailById(String id) {
        return categoryService.categoryDetailById(id);
    }

    @Override
    public Tag tagDetailById(String id) {
        return tagService.tagDetailById(id);
    }
}
