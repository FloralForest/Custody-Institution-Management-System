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
public class CourseEvaluateDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private  Integer id;

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
     * 评价id
     */
    private Integer evaluateId;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 评价人id
     */
    private Integer peopleId;

    /**
     * 用户名
     */
    private String userName;

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
    private LocalDateTime createEvaluateTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateEvaluateTime;

}
