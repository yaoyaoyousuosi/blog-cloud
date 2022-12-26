package org.cloud.blog.oss.util;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public class OSSClientUtil {
    private static OSS createClient() {
        return new OSSClientBuilder().build("",
                "", "");
    }

    public static String Upload(MultipartFile file, String type){
        OSS ossClient = createClient();
        String date = new DateTime().toString("yyyy/MM/dd");
        String objectName = "blog/" + type +"/"
                .concat(date + "/")
                .concat(UUID.randomUUID().toString())
                .concat(file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject("yixue-boot", objectName, inputStream);
            return "https://yixue-boot.oss-cn-hangzhou.aliyuncs.com/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            ossClient.shutdown();
        }
    }
}
