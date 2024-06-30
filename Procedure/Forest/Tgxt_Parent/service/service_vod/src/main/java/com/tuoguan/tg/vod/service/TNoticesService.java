package com.tuoguan.tg.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.TNotices;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.entityVo.NoticeUserVO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TNoticesService extends IService<TNotices> {

    //查询公告以及发布的用户
    Map<String, Object> findPageNotice(Page<TNotices> pageParam, NoticeUserVO noticeUserVO);

    //id查询公告以及发布的用户
    Map<String, Object> selectNoticeUser(Long id);

    //查询当前登录角色拥有的公告总数
    int countByGroup(Long group);
}
