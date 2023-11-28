package cn.yaol.easyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author yao
 * @Description 博客与模板连接表实体类
 * @Date 2023/9/3 8:45
 */
@TableName(value = "category_model")
@Data
public class CategoryModel {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章id
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 模板id
     */
    @TableField(value = "model_id")
    private Integer modelId;

    /**
     * 模板的顺序
     */
    @TableField(value = "model_sort")
    private Integer modelSort;

    /**
     * 模板输入内容
     */
    @TableField(value = "content")
    private String content;
}
