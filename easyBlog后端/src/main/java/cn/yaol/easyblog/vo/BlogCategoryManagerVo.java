package cn.yaol.easyblog.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Objects;

/**
 * @Author yao
 * @Description 将category的部分数据封装，用于用户页面的显示 与BlogCategoryVo添加了userid
 * @Date 2023/10/6 20:17
 */
public class BlogCategoryManagerVo {

    private Integer categoryId;

    /**
     * 博客排序
     */
    private Integer sort;

    /**
     * 封面
     */
    private String cover;

    /**
     * 博客名称
     */
    private String categoryName;

    /**
     * 博客简介
     */
    private String categoryBrief;

    /**
     * 博客权限（0:仅自己可见  1:他人也可见）
     */
    private String categorySelf;

    /**
     * 用户ID
     */
    private Integer userId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSort() {
        return sort;
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
        BlogCategoryManagerVo managerVo = (BlogCategoryManagerVo) o;
        return Objects.equals(categoryId, managerVo.categoryId) && Objects.equals(sort, managerVo.sort) && Objects.equals(cover, managerVo.cover) && Objects.equals(categoryName, managerVo.categoryName) && Objects.equals(categoryBrief, managerVo.categoryBrief) && Objects.equals(categorySelf, managerVo.categorySelf) && Objects.equals(userId, managerVo.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, sort, cover, categoryName, categoryBrief, categorySelf, userId);
    }

    @Override
    public String toString() {
        return "BlogCategoryManagerVo{" +
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
