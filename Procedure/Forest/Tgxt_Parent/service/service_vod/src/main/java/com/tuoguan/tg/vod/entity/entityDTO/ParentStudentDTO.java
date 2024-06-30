package com.tuoguan.tg.vod.entity.entityDTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ParentStudentDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 家长账号id
     */
    private Integer userId;

    /**
     * 家长姓名
     */
    private String parentName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 家长头像
     */
    private String parentAvatar;

    /**
     * 备注
     */
    private String remark;

    /**
     * 学生id
     */
    private Integer studentId;

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
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 登记时间
     */
    private LocalDate enrollmentDate;
}
