package org.cloud.blog.comment.service.impl;

import org.cloud.blog.comment.mapper.ArticleMapper;
import org.cloud.blog.comment.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource private ArticleMapper articleDao;
}
