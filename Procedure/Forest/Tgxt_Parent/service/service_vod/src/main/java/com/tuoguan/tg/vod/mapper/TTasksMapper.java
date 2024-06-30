package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TTasks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.entityDTO.StudentTaskDTO;
import com.tuoguan.tg.vod.entity.entityDTO.TeacherEvaluateDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 作业辅导 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-08
 */
public interface TTasksMapper extends BaseMapper<TTasks> {
    //查询指定id的学生作业辅导
    @Select("SELECT \n" +
            "t_students.student_name,gender,age,photo,phone_number,\n" +
            "t_teachers.teacher_name,`level`,course_major,hire_date, \n" +
            "t_tasks.*\n" +
            "            FROM t_students\n" +
            "            INNER JOIN t_tasks ON t_students.id = t_tasks.student_id\n" +
            "            INNER JOIN t_teachers ON t_tasks.teacher_id = t_teachers.id\n" +
            "            WHERE t_students.id = #{id}")
    List<StudentTaskDTO> studentTask(long id);

}
