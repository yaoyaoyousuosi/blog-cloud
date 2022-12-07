package org.cloud.blog.producer.controller;

import org.cloud.blog.common.utils.Response;
import org.cloud.blog.producer.service.ProducerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/producer/publish")
public class ProducerController {

    @Resource private ProducerService producerService;

    @PostMapping("/")
    public Response send(@RequestBody Map<String, String> data){
        producerService.send(data);
        return Response.ok(null);
    }

}
