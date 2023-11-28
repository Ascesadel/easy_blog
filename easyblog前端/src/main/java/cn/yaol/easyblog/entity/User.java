package cn.yaol.easyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author yao
 * @Description 用户实体类
 * @Date 2023/9/5 8:10
 */
@TableName(value = "blog_user")
@Data
public class User {

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField(value = "tname")
    private String tname;

    /**
     * 盐值，用于加密
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 用户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户状态 0:启用 1:禁用
     */
    @TableField(value = "status")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastTime;

}
