package cn.yaol.easyblog.vo;

import cn.yaol.easyblog.entity.CustomDateTimeSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.Objects;

/**
 * @Author yao
 * @Description  获取单个category的部分数据，用于博客详情界面的简介
 * @Date 2023/9/22 14:46
 */
public class CategoryInfoVo {

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

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
     * 用户昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonSerialize(using= CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryBrief() {
        return categoryBrief;
    }

    public void setCategoryBrief(String categoryBrief) {
        this.categoryBrief = categoryBrief;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryInfoVo that = (CategoryInfoVo) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryBrief, that.categoryBrief) && Objects.equals(username, that.username) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, categoryBrief, username, createTime);
    }

    @Override
    public String toString() {
        return "CategoryInfoVo{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryBrief='" + categoryBrief + '\'' +
                ", username='" + username + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
