package cn.yaol.easyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author yao
 * @Description 储存用户自己界面的排序
 * @Date 2023/10/2 14:49
 */
@TableName(value = "blog_sort")
@Data
public class BlogSort {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户查询所保存的ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 博客ID
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 博客排序(用户指定的顺序)
     */
    @TableField(value = "sort")
    private Integer sort;
}
