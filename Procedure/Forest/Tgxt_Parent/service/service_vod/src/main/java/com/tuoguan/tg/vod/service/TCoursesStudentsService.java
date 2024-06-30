package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TCoursesStudents;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程学生关联表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-17
 */
public interface TCoursesStudentsService extends IService<TCoursesStudents> {

    //选课
    boolean selectCourse(int studentId, int courseId);

    //取消选课
    int removeCourse(int courseId, int studentId);
}
