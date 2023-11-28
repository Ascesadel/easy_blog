package cn.yaol.easyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author yao
 * @Description blog_model对应的模型
 * @Date 2023/8/31 13:09
 */
@TableName(value = "blog_model")
@Data
public class BlogModel implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板的标题
     */
    @TableField(value = "model_title")
    private String modelTitle;

    /**
     * 模板css结构的左边部分
     */
    @TableField(value = "cssLeft")
    private String cssLeft;

    /**
     * 模板css结构的右边部分
     */
    @TableField(value = "cssRight")
    private String cssRight;

    /**
     * 创建模板的作者id
     */
    @TableField(value = "create_userid")
    private Integer createUserid;

    /**
     * 模板的类型（0为仅自己使用，1为共享）
     */
    @TableField(value = "type")
    private String type;
}
