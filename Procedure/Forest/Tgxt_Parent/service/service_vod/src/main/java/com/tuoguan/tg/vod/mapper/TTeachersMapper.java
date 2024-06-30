package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TTeachers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.entityDTO.CourseEvaluateDTO;
import com.tuoguan.tg.vod.entity.entityDTO.TeacherEvaluateDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 教师表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-07
 */
public interface TTeachersMapper extends BaseMapper<TTeachers> {

    //查询指定id教师的所有评论
    @Select("SELECT t_teachers.*, t_teacher_evaluate.*, t_users.username, " +
            "t_teacher_evaluate.id AS evaluateId, " +
            "t_teacher_evaluate.create_time AS createEvaluateTime, " +
            "t_teacher_evaluate.update_time AS updateEvaluateTime " +
            "FROM t_teachers " +
            "INNER JOIN t_teacher_evaluate ON t_teachers.id = t_teacher_evaluate.teacher_id " +
            "INNER JOIN t_users ON t_teacher_evaluate.people_id = t_users.id " +
            "WHERE t_teachers.id = #{id} AND t_teacher_evaluate.is_deleted = 0")
    List<TeacherEvaluateDTO> selectEvaluate(long id);

}
