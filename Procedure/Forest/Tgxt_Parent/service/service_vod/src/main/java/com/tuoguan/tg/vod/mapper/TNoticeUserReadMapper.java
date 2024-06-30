package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TNoticeUserRead;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户阅读公告记录表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-26
 */
public interface TNoticeUserReadMapper extends BaseMapper<TNoticeUserRead> {

    //查询是否已有数据
    @Select("SELECT COUNT(*) FROM `t_notice_user_read` WHERE notice_id = #{noticeId} AND user_id = #{userId}")
    int getRead(@Param("noticeId") long noticeId, @Param("userId") long userId);

    //查询已读公告数
    @Select("SELECT COUNT(*) FROM `t_notice_user_read` WHERE user_id = #{userId}")
    int countByUserId(@Param("userId") long userId);
}
