package cn.yaol.easyblog.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Objects;

/**
 * @Author yao
 * @Description 博客详情界面用于 显示下一个和上一个博客
 * @Date 2023/9/27 8:44
 */
public class CategoryInfoPageVo {

    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 博客排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 博客名称
     */
    @TableField(value = "category_name")
    private String categoryName;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryInfoPageVo() {
    }

    public CategoryInfoPageVo(Integer categoryId, Integer sort, String categoryName) {
        this.categoryId = categoryId;
        this.sort = sort;
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryInfoPageVo that = (CategoryInfoPageVo) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(sort, that.sort) && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, sort, categoryName);
    }

    @Override
    public String toString() {
        return "CategoryInfoPageVo{" +
                "categoryId=" + categoryId +
                ", sort=" + sort +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
