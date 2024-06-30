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
public class StudentCourseIntelDTO {
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
    private Integer isDeleted;

    /**
     * 学生id
     * SQL已经处理了映射关系
     */
    private Long studentId;

    /**
     * 分类名称
     */
    private String classifyName;

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
