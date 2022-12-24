package org.cloud.blog.admin.controller;

import org.cloud.blog.admin.service.ArticleService;
import org.cloud.blog.admin.util.PageUtil;
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
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/front/article")
public class ArticleController {


    @Resource
    private ArticleService articleService;

    @PostMapping("/list")
    public Response list(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = articleService.articleList(pageUtil);
        return Response.ok(data);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable String id){
        Boolean bool = articleService.deleteArticleById(id);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }
}
