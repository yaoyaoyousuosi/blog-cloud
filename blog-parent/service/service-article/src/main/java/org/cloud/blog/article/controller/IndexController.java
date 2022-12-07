package org.cloud.blog.article.controller;

import org.cloud.blog.article.domain.Article;
import org.cloud.blog.article.domain.Category;
import org.cloud.blog.article.domain.Tag;
import org.cloud.blog.article.service.IndexService;
import org.cloud.blog.article.vo.ArchivesVo;
import org.cloud.blog.article.vo.BodyVo;
import org.cloud.blog.common.utils.PageVo;
import org.cloud.blog.common.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article/index")
public class IndexController {

    @Resource private IndexService indexService;

    @PostMapping("/articles")
    public Response queryArticles(@RequestBody PageVo vo){
        List<Article> articles = indexService.queryArticles(vo);
        return Response.ok(articles).setMsg(null);
    }

    @PostMapping("/view/{id}")
    public Response articleView(@PathVariable("id") String id){
        Article article = indexService.articleView(id);
        return Response.ok(article).setMsg(null);
    }

    @GetMapping("/tag/hot")
    public Response hotTags(){
        List<Tag> tags = indexService.hotTags();
        return Response.ok(tags).setMsg(null);
    }

    @PostMapping("/article/hot")
    public Response articleHot(){
        List<Article> articleHots = indexService.articleHot();
        return Response.ok(articleHots).setMsg(null);
    }

    @PostMapping("/article/new")
    public Response articleNews(){
        List<Article> articleNews = indexService.articleNews();
        return Response.ok(articleNews).setMsg(null);
    }

    @PostMapping("/article/listArchives")
    public Response articleListArchives(){
        List<ArchivesVo> articleArchives = indexService.articleListArchives();
        return Response.ok(articleArchives).setMsg(null);
    }

    @GetMapping("/article/search")
    public Response articleSearch(@RequestParam("keyword") String keyword){
        List<BodyVo> vos = indexService.articleSearch(keyword);
        return Response.ok(vos).setMsg(null);
    }

    @GetMapping("/category/detail")
    public Response categoryDetails(){
        List<Category> categories = indexService.categoryDetails();
        return Response.ok(categories).setMsg(null);
    }

    @GetMapping("/category/detail/{id}")
    public Response categoryDetailById(@PathVariable String id){
        Category category = indexService.categoryDetailById(id);
        return Response.ok(category).setMsg(null);
    }

    @GetMapping("/tag/detail")
    public Response tagDetails(){
        List<Tag> tags = indexService.tagDetails();
        return Response.ok(tags).setMsg(null);
    }

    @GetMapping("/tag/detail/{id}")
    public Response tagDetailById(@PathVariable String id){
        Tag tag = indexService.tagDetailById(id);
        return Response.ok(tag).setMsg(null);
    }
}
