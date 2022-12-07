package org.cloud.blog.ucenter.client.impl;

import org.cloud.blog.common.utils.Response;
import org.cloud.blog.ucenter.client.ProducerClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProducerClientImpl implements ProducerClient {
    @Override
    public Response send(Map<String, String> data) {
        return Response.ok(null).setCode(5000).setMsg(null);
    }
}
