package com.tuoguan.tg.vod.service.impl;

import com.tuoguan.tg.vod.entity.TNoticeUserRead;
import com.tuoguan.tg.vod.mapper.TNoticeUserReadMapper;
import com.tuoguan.tg.vod.service.TNoticeUserReadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户阅读公告记录表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-26
 */
@Service
public class TNoticeUserReadServiceImpl extends ServiceImpl<TNoticeUserReadMapper, TNoticeUserRead> implements TNoticeUserReadService {
    @Resource
    private TNoticeUserReadMapper tNoticeUserReadMapper;

    //查询是否已有数据
    @Override
    public int getRead(Long noticeId, Long userId) {
       return tNoticeUserReadMapper.getRead(noticeId, userId);
    }

    //查询已读公告数
    @Override
    public int countByUserId(Long userId) {
        return tNoticeUserReadMapper.countByUserId(userId);
    }
}
