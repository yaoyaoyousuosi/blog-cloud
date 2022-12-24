package org.cloud.blog.comment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import org.cloud.blog.comment.dao.CommentDao;
import org.cloud.blog.comment.domain.Comment;
import org.cloud.blog.comment.domain.CommentTwo;
import org.cloud.blog.comment.mapper.ArticleMapper;
import org.cloud.blog.comment.service.CommentService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource private MongoTemplate mongoTemplate;
    @Resource private CommentDao commentDao;
    @Resource private ArticleMapper articleMapper;

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreateDate(new Date());
        comment.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        comment.setLevelTwoComment(new ArrayList<>());
        commentDao.insert(comment);
        String articleId = comment.getArticleId();
        Long commentCounts = commentDao.countCommentsByArticleId(articleId);
        try {
            articleMapper.updateCommentCounts(articleId, commentCounts);
        }catch (Exception e) {
            System.out.println(e);
        }
        return comment;
    }

    @Override
    public List<Comment> queryCommentsById(String articleId) {
        List<Comment> commentList = commentDao.findCommentsByArticleIdOrderByCreateDateDesc(articleId);
        return commentList;
    }

    @Override
    public CommentTwo createCommentTwo(CommentTwo reply) {
        reply.set_id(String.valueOf(IdUtil.getSnowflakeNextId()));
        reply.setCreateDate(new Date());
        System.out.println(reply);
        Query query = Query.query(Criteria.where("_id").is(reply.getParent()));
        Update update = new Update();
        update.push("levelTwoComment", reply);
        mongoTemplate.updateFirst(query, update, Comment.class);
        return reply;
    }


    @Override
    public Boolean deleteCommentById(String articleId) {
        Long count = commentDao.deleteByArticleId(articleId);
        if(count > 0){
            return true;
        }
        return false;
    }
}
