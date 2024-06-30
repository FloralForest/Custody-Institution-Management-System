package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TActivitysStudents;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 活动学生关联表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TActivitysStudentsMapper extends BaseMapper<TActivitysStudents> {

    int removeActivityStudent(@Param("activityId") Integer activityId, @Param("studentId") Integer studentId);
}
