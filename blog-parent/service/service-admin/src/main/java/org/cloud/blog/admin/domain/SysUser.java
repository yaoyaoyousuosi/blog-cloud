package org.cloud.blog.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
@Getter
@Setter
@TableName("ms_sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 是否管理员
     */
    private Boolean admin;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date lastLogin;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 状态
     */
    private String status;
}
