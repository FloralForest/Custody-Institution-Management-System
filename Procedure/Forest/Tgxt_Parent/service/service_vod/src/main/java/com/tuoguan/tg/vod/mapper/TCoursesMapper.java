package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TCourses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.entityDTO.CourseEvaluateDTO;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * <p>
 * 课程表	 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-15
 */
@Mapper
public interface TCoursesMapper extends BaseMapper<TCourses> {
    //查询选择该门课学生
    List<CourseStudentDTO> selectCourseStudent(long id);

    //查询指定id课程的所有评论
    List<CourseEvaluateDTO> selectEvaluate(long id);
}
