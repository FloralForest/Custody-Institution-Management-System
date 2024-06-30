package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_courses")//对应数据库表名
public class TCourses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程分类
     */
    private Integer classifyId;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 课程费用
     */
    private String courseFees;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除(1:删除)
     */
    @TableLogic//逻辑删除
    private Integer isDeleted;


    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)//比如某些字段没有但需要使用
    private Map<String,Object> param = new HashMap<>();
}
