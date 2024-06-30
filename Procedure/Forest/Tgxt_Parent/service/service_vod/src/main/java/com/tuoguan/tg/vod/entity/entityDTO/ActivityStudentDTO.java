package com.tuoguan.tg.vod.entity.entityDTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class ActivityStudentDTO {
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
    private String activityType;

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
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 与家长关系
     */
    private String relationship;

    /**
     * 学生照片
     */
    private String photo;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 登记时间
     */
    private LocalDate enrollmentDate;

}
