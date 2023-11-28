package cn.yaol.easyblog.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Objects;

/**
 * @Author yao
 * @Description 添加新博客的部分数据封装
 * @Date 2023/9/7 8:12
 */
public class CategoryAddVo {

    /**
     * @description: 对应博客的id
     * @author yaol
     * @date 2023/9/17 11:00
     * @version 1.0
     */
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
     * 博客权限（0:仅自己可见  1:他人也可见）
     */
    @TableField(value = "category_self")
    private String categorySelf;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    public Integer getSort() {
        return sort;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getCategorySelf() {
        return categorySelf;
    }

    public void setCategorySelf(String categorySelf) {
        this.categorySelf = categorySelf;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryAddVo that = (CategoryAddVo) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(sort, that.sort) && Objects.equals(cover, that.cover) && Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryBrief, that.categoryBrief) && Objects.equals(categorySelf, that.categorySelf) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, sort, cover, categoryName, categoryBrief, categorySelf, userId);
    }

    @Override
    public String toString() {
        return "CategoryAddVo{" +
                "categoryId=" + categoryId +
                ", sort=" + sort +
                ", cover='" + cover + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryBrief='" + categoryBrief + '\'' +
                ", categorySelf='" + categorySelf + '\'' +
                ", userId=" + userId +
                '}';
    }
}
