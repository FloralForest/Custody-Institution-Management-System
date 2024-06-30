package com.tuoguan.tg.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.TActivitys;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.entityDTO.ActivityStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityVo.ActivityTeacherVO;
import com.tuoguan.tg.vod.entity.entityVo.CourseTeacherVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表	 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TActivitysService extends IService<TActivitys> {

    //查询活动以及关联的负责人还有分页
    Map<String, Object> findPageActivity(Page<TActivitys> pageParam, ActivityTeacherVO activityTeacherVO);

    //查询选择该活动的所有学生
    List<ActivityStudentDTO> selectActivityStudent(long id);
}
