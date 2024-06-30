package com.tuoguan.tg.vod.entity.entityVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CourseTeacherVO {
    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
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
     * 教师id
     */
    private Integer teacherId;
}
