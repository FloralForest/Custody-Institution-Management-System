package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TCoursesStudents;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程学生关联表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-17
 */
public interface TCoursesStudentsMapper extends BaseMapper<TCoursesStudents> {
    int removeCourseStudent(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
}
