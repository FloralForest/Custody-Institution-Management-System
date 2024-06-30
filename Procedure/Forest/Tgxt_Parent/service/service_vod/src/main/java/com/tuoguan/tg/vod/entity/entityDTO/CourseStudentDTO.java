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
public class CourseStudentDTO {
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
