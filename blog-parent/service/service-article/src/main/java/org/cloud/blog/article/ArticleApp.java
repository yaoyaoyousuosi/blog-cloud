package org.cloud.blog.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ArticleApp {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApp.class);
    }
}
