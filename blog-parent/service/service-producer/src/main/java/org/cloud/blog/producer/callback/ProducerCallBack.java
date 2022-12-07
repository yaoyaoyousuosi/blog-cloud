package org.cloud.blog.producer.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProducerCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    public static final ConcurrentHashMap<String, String> ROLLBACK_MESSAGE_CONTAINER = new ConcurrentHashMap<>();
    private static final String RETURN_MESSAGE_VERSION = "spring_returned_message_correlation";
    @Resource private RabbitTemplate rabbitTemplate;
    @Resource private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String version = null;
        if(null != correlationData){
            version = correlationData.getId();
        }
        if(!ack){
            String message = stringRedisTemplate.opsForValue().get(version);
            if(null != message){
                ROLLBACK_MESSAGE_CONTAINER.put(version, message);
            }
        }else {
            if(ROLLBACK_MESSAGE_CONTAINER.size() > 0){
                ROLLBACK_MESSAGE_CONTAINER.remove(version);
            }
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println(replyText);
        String version = ((String) message.getMessageProperties().getHeader(RETURN_MESSAGE_VERSION));
        BufferedWriter writer = null;
        try {
            String path = System.getProperty("user.dir");
            writer = new BufferedWriter(new FileWriter(path + "\\log.txt", true));
            String log = new Date().toString() + "消息被回退:" +message;
            writer.write(log);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public StringRedisTemplate getStringRedisTemplate(){
        return this.stringRedisTemplate;
    }
}
