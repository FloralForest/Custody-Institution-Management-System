package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程评价
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TCourseEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程评价
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 评价人id
     */
    private Integer peopleId;

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除(1:删除)
     */
    @TableLogic//逻辑删除
    private Integer isDeleted;


}
