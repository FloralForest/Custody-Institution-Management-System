package com.tuoguan.tg.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.TCourses;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.entityDTO.CourseEvaluateDTO;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import com.tuoguan.tg.vod.entity.entityVo.CourseTeacherVO;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 课程表	 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-15
 */
public interface TCoursesService extends IService<TCourses> {
    //查询课程以及关联的教师还有分页
    Map<String, Object> findPageCourse(Page<TCourses> pageParam, CourseTeacherVO courseTeacherVO);

    //查询选择该门课的所有学生
    List<CourseStudentDTO> selectCourseStudent(long id);

    //查询指定id的课程的评论
    List<CourseEvaluateDTO> selectEvaluate(long id);
}
