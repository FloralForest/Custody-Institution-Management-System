package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 活动学生关联表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TActivitysStudents implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
