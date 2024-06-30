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
 * 学生表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_students")//对应数据库表名
public class TStudents implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)//比如某些字段没有但需要使用
    private Map<String,Object> param = new HashMap<>();

}
