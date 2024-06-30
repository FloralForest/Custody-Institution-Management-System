package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-13
 */
public interface TVideoService extends IService<TVideo> {

    Map<String,Object> uploadVideo();

    //删除视频
    void removeVideo(String fileId);

    //查询视频
    TVideo getVideo(Long courseId);
}
