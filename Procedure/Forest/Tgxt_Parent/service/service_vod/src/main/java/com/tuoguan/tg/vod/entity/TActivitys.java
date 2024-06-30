package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_activitys")//对应数据库表名
public class TActivitys implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动简介
     */
    private String description;

    /**
     * 活动类型
     */
    private Integer activityClassifyId;

    /**
     * 活动地点
     */
    private String location;

    /**
     * 活动代理教师
     */
    private Integer activityAgent;

    /**
     * 活动主办方(教师个人或者机构)
     */
    private String organizer;

    /**
     * 活动费用
     */
    private String activityFee;

    /**
     * 活动封面
     */
    private String activityCover;

    /**
     * 活动开始时间
     */
    private LocalDate startTime;

    /**
     * 活动结束时间
     */
    private LocalDate endTime;

    /**
     * 报名开始时间
     */
    private LocalDate registrationTime;

    /**
     * 报名结束时间
     */
    private LocalDate expirationTime;

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
    private Map<String, Object> param = new HashMap<>();

}
