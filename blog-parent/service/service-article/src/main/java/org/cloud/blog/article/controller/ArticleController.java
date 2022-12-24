package org.cloud.blog.article.controller;

import org.cloud.blog.article.service.ArticleService;
import org.cloud.blog.article.vo.ArticleVo;
import org.cloud.blog.common.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource private ArticleService articleService;

    @PostMapping("/publish")
    public Response articlePublish(@RequestBody ArticleVo vo, @RequestHeader("authId") String authId){
        String articleId = articleService.articlePublish(vo, authId);
        return Response.ok(articleId).setMsg("发布成功");
    }
}
