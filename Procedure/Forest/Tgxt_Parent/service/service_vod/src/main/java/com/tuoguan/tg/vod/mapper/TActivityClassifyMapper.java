package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TActivityClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 活动分类表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TActivityClassifyMapper extends BaseMapper<TActivityClassify> {

    @Select("SELECT classify_name \n" +
            "FROM `t_activity_classify`")
    List<String> selectByName();
}
