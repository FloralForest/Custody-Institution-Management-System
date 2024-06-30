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
 * 每日食谱表
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TRecipes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每日食谱ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 食谱名称
     */
    private String recipeName;

    /**
     * 食谱图片
     */
    private String recipeCover;

    /**
     * 食谱原料
     */
    private String ingredient;

    /**
     * 分类
     */
    private String recipeClassify;

    /**
     * 日期
     */
    private LocalDate recipeDate;

    /**
     * 时段（早/中/晚）
     */
    private String timeSpan;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
