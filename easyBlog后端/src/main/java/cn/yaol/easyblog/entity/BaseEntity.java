package cn.yaol.easyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author yao
 * @Description 公共基础实体类
 * @Date 2023/8/31 13:19
 */
@Data
public class BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建日期
     */
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新日期
     */
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}
