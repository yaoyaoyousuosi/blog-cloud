package org.cloud.blog.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class);
    }
}