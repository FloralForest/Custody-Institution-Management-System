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
public class StudentTaskDTO {
    private static final long serialVersionUID = 1L;
    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 学生照片
     */
    private String photo;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 头衔(1:高级教师,0:普通教师)
     */
    private Integer level;

    /**
     * 课程专业
     */
    private String courseMajor;

    /**
     * 入职日期
     */
    private LocalDate hireDate;

    /**
     * 作业辅导ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课目
     */
    private String curriculum;

    /**
     * 状态
     */
    private String state;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 作业内容
     */
    private String taskContent;

    /**
     * 大致辅导内容
     */
    private String counsellingContent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
