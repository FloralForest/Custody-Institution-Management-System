package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TParentsStudents;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 家长学生关联表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-14
 */
public interface TParentsStudentsMapper extends BaseMapper<TParentsStudents> {

    //根据学生id查询数据
    @Select("SELECT t_parents_students.*\n" +
            "        FROM t_parents_students\n" +
            "        WHERE t_parents_students.student_id= #{id}")
    TParentsStudents findByStudentId(Long id);
}
