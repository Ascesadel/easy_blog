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
 * @Description 博客表的实体类
 * @Date 2023/9/4 10:28
 */
@TableName(value = "blog_category")
@Data
public class BlogCategory {
    /**
     * 博客ID
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 博客排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 博客名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 博客简介
     */
    @TableField(value = "category_brief")
    private String categoryBrief;

    /**
     * 博客分类（0:草稿  1:博客）
     */
    @TableField(value = "category_type")
    private String categoryType;

    /**
     * 博客权限（0:仅自己可见  1:他人也可见）
     */
    @TableField(value = "category_self")
    private String categorySelf;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
