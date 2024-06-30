package com.tuoguan.tg.vod.entity.entityDTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class StudentActivityIntelDTO {
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
    private String classifyName;

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
    private Integer isDeleted;

    /**
     * 学生id
     * SQL已经处理了映射关系
     */
    private Long studentId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 头衔(1:高级教师,0:普通教师)
     */
    private Integer level;

    /**
     * 教师头像
     */
    private String teachersAvatar;

}
