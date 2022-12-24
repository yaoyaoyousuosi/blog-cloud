package org.cloud.blog.oss.controller;

import org.cloud.blog.common.utils.Response;
import org.cloud.blog.oss.service.OssService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/oss")
public class OssController {

    @Resource private OssService ossService;

    @PostMapping("/user/avatar")
    public Response uploadAvatar(MultipartFile file){
        String avatar = ossService.uploadAvatar(file);
        return Response.ok(avatar).setMsg(null);
    }

    @PostMapping("/article")
    public Response uploadArticle(@RequestParam("image") MultipartFile file){
        String path = ossService.uploadArticle(file);
        return Response.ok(path).setMsg(null);
    }

    @CrossOrigin
    @PostMapping("/blog/sources/{type}")
    public Response uploadBlogSources(MultipartFile file, @PathVariable String type){
        String path = ossService.uploadBlogSources(file, type);
        return Response.ok(path).setMsg(null);
    }
}
