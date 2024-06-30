package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 家长表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_parents")//对应数据库表名
public class TParents implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 家长id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 家长姓名
     */
    private String parentName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 家长头像
     */
    private String parentAvatar;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

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
