package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代课表
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TProxys implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代课id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 代课老师id
     */
    private Integer teacherId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
