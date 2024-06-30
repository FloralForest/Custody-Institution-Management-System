package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TNotices;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TNoticesMapper extends BaseMapper<TNotices> {

    //查询登录角色拥有的公告数量 当角色不为0（管理员）时走查询条件 group为角色拥有的公告，3为公共公告
    @Select("<script>" +
            "SELECT COUNT(*) FROM `t_notices`" +
            "<where>" +
            "`is_deleted` = 0" +
            "  <if test='group != 0'>" +
            "    AND (`group` = #{group} OR `group` = 3)" +
            "  </if>" +
            "</where>" +
            "</script>")
    int countByGroup(Long group);
}
