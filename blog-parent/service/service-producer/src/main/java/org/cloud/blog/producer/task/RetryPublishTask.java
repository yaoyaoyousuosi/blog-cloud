package org.cloud.blog.producer.task;

import com.google.gson.Gson;
import org.cloud.blog.producer.callback.ProducerCallBack;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RetryPublishTask {
    @Resource private RabbitTemplate rabbitTemplate;
    @Resource private ProducerCallBack producerCallBack;

    @Scheduled(cron = "* 0/10 * * * ? ")
    public void retryPublish(){
        ConcurrentHashMap<String, String> messageContainer = ProducerCallBack.ROLLBACK_MESSAGE_CONTAINER;
        for (Map.Entry entry : messageContainer.entrySet()){
            String version = entry.getKey().toString();
            String body = entry.getValue().toString();
            Map<String, String> payload = new Gson().fromJson(body, Map.class);
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(version);
            rabbitTemplate.convertAndSend(payload.get("exchange"),
                    payload.get("routingKey"), payload, correlationData);
        }
    }
}
