package org.cloud.blog.article.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author whj
 * @since 2022-11-30
 */
@Getter
@Setter
@TableName("ms_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 简介
     */
    private String summary;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览数量
     */
    private String viewCounts;

    /**
     * 是否置顶
     */
    private Integer weight;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 内容id
     */
    private String bodyId;

    /**
     * 类别id
     */
    private String categoryId;

    @TableField(exist = false)
    private List<Tag> tags;

    @TableField(exist = false)
    private Category category;

    @TableField(exist = false)
    private String author;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private ArticleBody body;


}
