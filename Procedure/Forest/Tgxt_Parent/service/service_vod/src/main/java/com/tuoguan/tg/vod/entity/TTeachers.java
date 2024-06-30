package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 教师表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_teachers")//对应数据库表名
public class TTeachers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableLogic//逻辑删除
    private Integer isDeleted;


}
