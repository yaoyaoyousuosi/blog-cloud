package org.cloud.blog.article.service.impl;

import cn.hutool.core.util.IdUtil;
import org.cloud.blog.article.domain.ArticleBody;
import org.cloud.blog.article.mapper.ArticleBodyMapper;
import org.cloud.blog.article.service.ArticleBodyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements ArticleBodyService {

    @Override
    public String insertArticleBody(Map<String, String> body) {
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(body.get("content"));
        articleBody.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        articleBody.setContentHtml(body.get("contentHtml"));
        articleBody.setArticleId(body.get("articleId"));
        articleBody.setSummary(body.get("summary"));
        baseMapper.insert(articleBody);
        return articleBody.getId();
    }
}
