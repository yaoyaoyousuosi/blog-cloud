package org.cloud.blog.admin.client;

import org.cloud.blog.common.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-comment")
public interface CommentClient {
    @DeleteMapping("/comment/{id}")
    Response deleteCommentById(@PathVariable(value = "id") String articleId);
}
