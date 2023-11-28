package cn.yaol.easyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author yao
 * @Description 城市实体类
 * @Date 2023/11/26 8:10
 */
@TableName(value = "blog_city")
@Data
public class BlogCity {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地区
     */
    @TableField(value = "area")
    private String area;

    /**
     * 城市名称
     */
    @TableField(value = "city")
    private String city;

    /**
     * 城市对应的气象代码
     */
    @TableField(value = "value")
    private Integer value;

}
