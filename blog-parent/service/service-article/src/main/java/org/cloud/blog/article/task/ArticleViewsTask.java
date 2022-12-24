package org.cloud.blog.article.task;

import org.cloud.blog.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ArticleViewsTask {

    @Resource private ArticleService articleService;

    @Scheduled(cron = "* 0/30 * * * ? ")
    public void updateViews() {
        articleService.updateViews();
    }
}
