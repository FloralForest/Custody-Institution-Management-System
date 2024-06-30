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
public class TeacherEvaluateDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 教师id
     */
    private Long id;

    /**
     * 账号
     */
    private Integer userId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 教师简介
     */
    private String intro;

    /**
     * 教师资历,一句话说明教师
     */
    private String career;

    /**
     * 头衔(1:高级教师,0:普通教师)
     */
    private Integer level;

    /**
     * 教师头像
     */
    private String teachersAvatar;

    /**
     * 课程专业
     */
    private String courseMajor;

    /**
     * 入职日期
     */
    private LocalDate hireDate;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 评价id
     */
    private Integer evaluateId;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 评价人id
     */
    private Integer peopleId;

    /**
     * 评分
     */
    private Float fraction;

    /**
     * 课程评价
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
