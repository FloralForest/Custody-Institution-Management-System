package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TActivitys;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.entityDTO.ActivityStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;

import java.util.List;

/**
 * <p>
 * 活动表	 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TActivitysMapper extends BaseMapper<TActivitys> {

    //查询选择该活动的学生
    List<ActivityStudentDTO> selectActivityStudent(long id);
}
