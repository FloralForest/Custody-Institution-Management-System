package com.tuoguan.tg.vod.entity.entityVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUserVO {
    private static final long serialVersionUID = 1L;

    /**
     * 公告id
     */
    private Long id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告优先级
     */
    private Integer priority;

    /**
     * 用户id
     */
    private Integer publisher;

    /**
     * 角色(0:管理员,1:教师,2:家长)
     */
    private Integer role;

    /**
     * 发布时间--开始
     */
    private String publishDateBegin;

    /**
     * 发布时间--结束
     */
    private String publishDateEnd;
}
