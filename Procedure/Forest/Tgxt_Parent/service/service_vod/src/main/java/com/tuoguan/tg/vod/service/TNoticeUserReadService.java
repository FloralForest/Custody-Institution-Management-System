package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TNoticeUserRead;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户阅读公告记录表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-26
 */
public interface TNoticeUserReadService extends IService<TNoticeUserRead> {

    //查询是否已有数据
    int getRead(Long noticeId, Long userId);

    //查询已读公告数
    int countByUserId(Long userId);
}
