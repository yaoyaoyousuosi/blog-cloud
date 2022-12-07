package org.cloud.blog.ucenter.client;

import org.cloud.blog.common.utils.Response;
import org.cloud.blog.ucenter.client.impl.ProducerClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component
@FeignClient(value = "service-producer",fallback = ProducerClientImpl.class)
public interface ProducerClient {
    @PostMapping("/producer/publish/")
    Response send(@RequestBody Map<String, String> data);
}
