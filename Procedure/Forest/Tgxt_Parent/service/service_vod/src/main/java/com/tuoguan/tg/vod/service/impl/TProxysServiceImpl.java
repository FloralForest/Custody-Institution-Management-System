package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tuoguan.tg.vod.entity.TCoursesStudents;
import com.tuoguan.tg.vod.entity.TProxys;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.mapper.TProxysMapper;
import com.tuoguan.tg.vod.service.TProxysService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 代课表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-02
 */
@Service
public class TProxysServiceImpl extends ServiceImpl<TProxysMapper, TProxys> implements TProxysService {
    @Resource
    private TProxysMapper tProxysMapper;

    //代课；判定是否重复
    @Override
    public boolean saveProxy(TProxys tProxys) {
        // 检查是否已经选过这门课
        LambdaQueryWrapper<TProxys> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TProxys::getCourseId, tProxys.getCourseId());
        if (this.count(lambdaQueryWrapper) > 0) {
            return false;
        }
        // 添加代课记录
        TProxys tCoursesProxy = new TProxys();
        tCoursesProxy.setCourseId(tProxys.getCourseId());
        tCoursesProxy.setTeacherId(tProxys.getTeacherId());
        return this.save(tCoursesProxy);
    }

    //根据课程id查询课程代课老师
    @Override
    public TTeachers getCourseId(Long id) {
        return tProxysMapper.selectProxy(id);
    }
}
