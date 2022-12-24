package org.cloud.blog.admin.controller;

import org.cloud.blog.admin.domain.Category;
import org.cloud.blog.admin.service.CategoryService;
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
@RequestMapping("/front/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping("/list")
    public Response list(@RequestBody PageUtil pageUtil){
        Map<String, Object> data = categoryService.categoryList(pageUtil);
        return Response.ok(data);
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id){
        Boolean bool = categoryService.deleteCategoryById(id);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }

    @PostMapping("/add")
    public Response add(@RequestBody Category category){
        Boolean bool = categoryService.add(category);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }


    @PostMapping("/update")
    public Response update(@RequestBody Category category){
        Boolean bool = categoryService.updateCategory(category);
        if (bool){
            return Response.ok(null);
        }else {
            return Response.error();
        }
    }
}
