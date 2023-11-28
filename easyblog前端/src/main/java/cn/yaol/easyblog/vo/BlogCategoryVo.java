package cn.yaol.easyblog.vo;

import java.util.Objects;

/**
 * @Author yao
 * @Description 将category的部分数据封装，用于前端页面的显示
 * @Date 2023/9/4 19:44
 */

public class BlogCategoryVo {

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
     * 博客分类（0:草稿  1:博客）
     */
    private String categorySelf;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogCategoryVo that = (BlogCategoryVo) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(sort, that.sort) && Objects.equals(cover, that.cover) && Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryBrief, that.categoryBrief) && Objects.equals(categorySelf, that.categorySelf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, sort, cover, categoryName, categoryBrief, categorySelf);
    }

    @Override
    public String toString() {
        return "BlogCategoryVo{" +
                "categoryId=" + categoryId +
                ", sort=" + sort +
                ", cover='" + cover + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryBrief='" + categoryBrief + '\'' +
                ", categorySelf='" + categorySelf + '\'' +
                '}';
    }
}
