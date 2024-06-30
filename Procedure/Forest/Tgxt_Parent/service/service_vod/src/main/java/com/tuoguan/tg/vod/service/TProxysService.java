package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TProxys;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.TTeachers;

/**
 * <p>
 * 代课表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-02
 */
public interface TProxysService extends IService<TProxys> {

    //代课；判定是否重复
    boolean saveProxy(TProxys tProxys);

    //根据课程id查询课程代课老师
    TTeachers getCourseId(Long id);
}
