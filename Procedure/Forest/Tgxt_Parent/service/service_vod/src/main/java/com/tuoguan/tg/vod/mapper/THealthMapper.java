package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.THealth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 学生健康情况表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-24
 */
public interface THealthMapper extends BaseMapper<THealth> {

    @Select("SELECT t_health.*\n" +
            "FROM t_health\n" +
            "WHERE t_health.student_id = #{studentId}")
    THealth getStudentId(Long studentId);
}
