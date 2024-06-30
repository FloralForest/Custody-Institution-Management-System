package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.THealth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学生健康情况表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-24
 */
public interface THealthService extends IService<THealth> {

    THealth getStudentId(Long studentId);
}
