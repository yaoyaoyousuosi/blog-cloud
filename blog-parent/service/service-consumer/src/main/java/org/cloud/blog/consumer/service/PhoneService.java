package org.cloud.blog.consumer.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;
import org.cloud.blog.consumer.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PhoneService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "phone")
    public void consumerPhoneMsg(Message<Map<String, String>> message) throws Exception {
        System.out.println("接收消息");
        Map<String, String> payload = message.getPayload();
        String version = payload.get("version");
        String phone = payload.get("phone");
        Boolean bool = RedisUtil.checkConsumerSecurity(stringRedisTemplate, version);
        if(bool){
            return;
        }
        send(phone, version);
    }

    public void send(String phone, String version) throws Exception {
        Client client = createClient();
        HashMap<String, String> data = new HashMap<>();
        String str = "0202";
        data.put("code", str);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("xhrblog在线平台")
                .setTemplateCode("SMS_254110806")
                .setPhoneNumbers(phone)
                .setTemplateParam(new Gson().toJson(data));
        RuntimeOptions runtime = new RuntimeOptions();
        SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
        Integer code = sendSmsResponse.getStatusCode();
        if(200 == code){
            stringRedisTemplate.opsForValue().set(version, phone, 60, TimeUnit.SECONDS);
        }
    }


    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId("LTAI5tKt7m1tKVWAnAhMvAn2")
                .setAccessKeySecret("A1SkmwCW92rF6x6VTV4C7DX5Hgxvsf");
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
}
