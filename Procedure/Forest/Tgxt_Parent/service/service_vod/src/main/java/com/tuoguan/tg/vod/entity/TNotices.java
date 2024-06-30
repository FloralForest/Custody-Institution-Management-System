package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TNotices implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公告id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布者id
     */
    private Integer publisher;

    /**
     * 发布日期
     */
    private LocalDate publishDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 公告查看群体(1教师,2家长,3全部)
     */
    @TableField("`group`")//正确处理保留字关键字
    private Integer group;

    /**
     * 优先级(1紧急,2重要,3普通)
     */
    private Integer priority;

    /**
     * 附件(仅支持图片)
     */
    private String attachments;

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

    @ApiModelProperty(value = "其他参数")
    @TableField(exist = false)//比如某些字段没有但需要使用
    private Map<String, Object> param = new HashMap<>();
}
