package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 作业辅导
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TTasks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作业辅导ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 辅导教师ID
     */
    private Integer teacherId;

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
