package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TProxys;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.TTeachers;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 代课表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-02
 */
public interface TProxysMapper extends BaseMapper<TProxys> {

    //根据课程id查询课程代课老师
    @Select("SELECT t_proxys.*, t_teachers.* \n" +
            "FROM t_teachers\n" +
            "INNER JOIN t_proxys ON t_teachers.id = t_proxys.teacher_id\n" +
            "WHERE t_proxys.course_id = #{id}")
    TTeachers selectProxy(Long id);
}
