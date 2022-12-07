package org.cloud.blog.producer.service.impl;

import com.google.gson.Gson;
import org.cloud.blog.producer.service.ProducerService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Resource private RabbitTemplate rabbitTemplate;
    @Resource private StringRedisTemplate stringRedisTemplate;

    public void send(Map<String, String> payload){
        payload.put("exchange", "phoneExchange");
        payload.put("routingKey", "phone");
        String version = payload.get("version");
        String body = new Gson().toJson(payload);
        stringRedisTemplate.opsForValue().set(version, body);
        rabbitTemplate.convertAndSend("phoneExchange", "phone",
                payload, new CorrelationData(version));
    }
}
