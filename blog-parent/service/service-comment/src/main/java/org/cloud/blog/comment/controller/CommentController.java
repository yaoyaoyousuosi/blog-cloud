package org.cloud.blog.comment.controller;

import org.cloud.blog.comment.domain.Comment;
import org.cloud.blog.comment.domain.CommentTwo;
import org.cloud.blog.comment.service.CommentService;
import org.cloud.blog.common.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource private CommentService commentService;

    @PostMapping("/")
    public Response createComment(@RequestBody Comment comment){
        Comment data = commentService.createComment(comment);
        return Response.ok(data).setMsg("评论成功");
    }

    @PostMapping("/two")
    public Response createCommentTwo(@RequestBody CommentTwo commentTwo){
        CommentTwo comment = commentService.createCommentTwo(commentTwo);
        return Response.ok(comment).setMsg("回复成功");
    }


    @GetMapping("/views/{id}")
    public Response queryCommentsById(@PathVariable("id") String articleId){
        List<Comment> commentList = commentService.queryCommentsById(articleId);
        return Response.ok(commentList).setMsg(null);
    }

}
