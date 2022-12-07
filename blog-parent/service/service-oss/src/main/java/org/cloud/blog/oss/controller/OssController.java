package org.cloud.blog.oss.controller;

import org.cloud.blog.common.utils.Response;
import org.cloud.blog.oss.service.OssService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
