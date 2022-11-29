package org.cloud.blog.consumer.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class RedisUtil {

    public static Boolean checkConsumerSecurity(StringRedisTemplate stringRedisTemplate, String version){
        String flag = stringRedisTemplate.opsForValue().get(version);
        if(!StringUtils.isEmpty(flag)){
            return true;
        }
        return false;
    }
}
