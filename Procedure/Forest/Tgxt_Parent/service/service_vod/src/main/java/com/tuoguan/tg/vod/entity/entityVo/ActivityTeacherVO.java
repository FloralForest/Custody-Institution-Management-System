package com.tuoguan.tg.vod.entity.entityVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
//用于条件查询
public class ActivityTeacherVO {
    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动分类
     */
    private Integer activityClassifyId;

    /**
     * 活动主办方(教师个人或者机构)
     */
    private String organizer;

    /**
     * 活动代理教师
     */
    private Integer activityAgent;

    /**
     * 活动开始时间--起始和结束
     */
    private String startTimeBegin;

    private String startTimeEnd;

    /**
     * 报名开始时间--起始和结束
     */
    private String registrationTimeBegin;

    private String registrationTimeEnd;

    /**
     * 报名结束时间--起始和结束
     */
    private String expirationTimeBegin;

    private String expirationTimeEnd;
}
