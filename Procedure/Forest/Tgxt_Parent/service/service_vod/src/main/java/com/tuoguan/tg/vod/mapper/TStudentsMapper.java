package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TStudents;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentCourseIntelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-13
 */
@Mapper
public interface TStudentsMapper extends BaseMapper<TStudents> {

    //查询指定id学生选择的所有课程
    List<StudentCourseIntelDTO> selectStudentCourse(long id);

    //查询指定id学生选择的所有活动
    List<StudentActivityIntelDTO> selectStudentActivity(long id);
}
