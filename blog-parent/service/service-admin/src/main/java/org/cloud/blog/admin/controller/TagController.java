package org.cloud.blog.admin.controller;

import org.cloud.blog.admin.domain.Admin;
import org.cloud.blog.admin.domain.Tag;
import org.cloud.blog.admin.service.TagService;
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
@RequestMapping("/front/tag")
public class TagController {

    @Resource private TagService tagService;

    @PostMapping("/list")
    public Response list(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = tagService.tagList(pageUtil);
        return Response.ok(data);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id){
        Boolean bool = tagService.deleteTagById(id);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

    @PostMapping("/add")
    public Response add(@RequestBody Tag tag){
        Boolean bool = tagService.add(tag);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }


    @PostMapping("/update")
    public Response update(@RequestBody Tag tag){
        Boolean bool = tagService.updateTag(tag);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }


}
