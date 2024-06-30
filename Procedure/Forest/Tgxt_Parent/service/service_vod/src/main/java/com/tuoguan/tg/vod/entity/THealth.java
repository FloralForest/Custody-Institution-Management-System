package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生健康情况表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class THealth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 健康id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 健康状况
     */
    @TableField("`condition`")//正确处理保留字关键字
    private String condition;

    /**
     * 最近一次体检时间
     */
    private LocalDate examinationDate;

    /**
     * 病例情况
     */
    private String medicalRecord;

    /**
     * 过敏情况
     */
    private String allergy;

    /**
     * 心里健康
     */
    private String mentalHealth;

    /**
     * 饮食习惯
     */
    private String eatingHabits;

    /**
     * 睡眠习惯
     */
    private String sleepCondition;

    /**
     * 其他注意事项
     */
    private String other;


}
