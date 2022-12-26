package org.cloud.blog.consumer.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.cloud.blog.consumer.util.RedisUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PhoneService {

    @Resource private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "phone")
    public void consumerPhoneMsg(Map<String,String> payload, Message message, Channel channel) throws Exception {
        System.out.println(payload);
        String version = payload.get("version");
        String phone = payload.get("phone");
        Boolean bool = RedisUtil.checkConsumerSecurity(stringRedisTemplate, version);
        long tag = message.getMessageProperties().getDeliveryTag();
        if(bool){
            channel.basicNack(tag, false, true);
            return;
        }
        send(phone, version);
        stringRedisTemplate.delete(version);
        channel.basicAck(tag, false);
    }

    public void send(String phone, String version) throws Exception {
        Client client = createClient();
        HashMap<String, String> data = new HashMap<>();
        String str = "0202";
        data.put("code", str);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("xhrblog在线平台")
                .setTemplateCode("")
                .setPhoneNumbers(phone)
                .setTemplateParam(new Gson().toJson(data));
        RuntimeOptions runtime = new RuntimeOptions();
        SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
        Integer code = sendSmsResponse.getStatusCode();
        if(200 == code){
            stringRedisTemplate.opsForValue().set(version+"#phone", phone, 60, TimeUnit.SECONDS);
        }
    }


    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId("")
                .setAccessKeySecret("");
        config.endpoint = "";
        return new Client(config);
    }
}
