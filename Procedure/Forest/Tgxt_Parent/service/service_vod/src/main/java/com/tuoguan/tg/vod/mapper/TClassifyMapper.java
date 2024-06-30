package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.entityVo.ClassifyVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-16
 */
public interface TClassifyMapper extends BaseMapper<TClassify> {

    @Select("SELECT classify_name \n" +
            "FROM `t_classify`")
    List<String> selectByName();
}
