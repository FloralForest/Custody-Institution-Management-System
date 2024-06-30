package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TActivitysStudents;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 活动学生关联表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TActivitysStudentsService extends IService<TActivitysStudents> {

    //选活动
    boolean selectActivity(int studentId, int activityId);

    //取消活动
    int removeActivity(int activityId, int studentId);
}
