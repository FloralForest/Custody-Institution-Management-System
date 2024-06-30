package com.tuoguan.tg.vod.service.impl;

import com.tuoguan.tg.vod.entity.THealth;
import com.tuoguan.tg.vod.mapper.THealthMapper;
import com.tuoguan.tg.vod.service.THealthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 学生健康情况表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-24
 */
@Service
public class THealthServiceImpl extends ServiceImpl<THealthMapper, THealth> implements THealthService {

    @Resource
    private THealthMapper tHealthMapper;
    @Override
    public THealth getStudentId(Long studentId) {
        return tHealthMapper.getStudentId(studentId);
    }
}
