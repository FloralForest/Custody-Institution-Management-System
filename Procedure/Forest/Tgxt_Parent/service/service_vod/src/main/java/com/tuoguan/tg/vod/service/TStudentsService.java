package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TStudents;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentCourseIntelDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-13
 */
public interface TStudentsService extends IService<TStudents> {

    //查询指定id学生选择的课程
    List<StudentCourseIntelDTO> selectStudentCourse(long id);
    //查询指定id的学生的活动
    List<StudentActivityIntelDTO> selectStudentActivity(long id);

    //查询学生以及学生家长
    Map<String, Object> findStudentParent(Long id);
}
