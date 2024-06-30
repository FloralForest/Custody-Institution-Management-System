package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 当前登录用户信息
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户角色
     */
    private Integer roles;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 用户id
     */
    private Integer userId;


}
