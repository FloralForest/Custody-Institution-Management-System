package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tuoguan.tg.vod.entity.TActivitysStudents;
import com.tuoguan.tg.vod.entity.TCoursesStudents;
import com.tuoguan.tg.vod.mapper.TActivitysMapper;
import com.tuoguan.tg.vod.mapper.TActivitysStudentsMapper;
import com.tuoguan.tg.vod.mapper.TStudentsMapper;
import com.tuoguan.tg.vod.service.TActivitysStudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 活动学生关联表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Service
public class TActivitysStudentsServiceImpl extends ServiceImpl<TActivitysStudentsMapper, TActivitysStudents> implements TActivitysStudentsService {

    @Resource
    private TActivitysMapper tActivitysMapper;

    @Resource
    private TStudentsMapper tStudentsMapper;

    @Resource
    private TActivitysStudentsMapper tActivitysStudentsMapper;

    @Override
    public boolean selectActivity(int studentId, int activityId) {
        // 检查学生和课程是否存在
        if (tStudentsMapper.selectById(studentId) == null || tActivitysMapper.selectById(activityId) == null) {
            return false;
        }
        // 检查是否已经选过这门课
        LambdaQueryWrapper<TActivitysStudents> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TActivitysStudents::getStudentId, studentId).eq(TActivitysStudents::getActivityId, activityId);
        if (this.count(lambdaQueryWrapper) > 0) {
            return false;
        }
        // 添加选课记录
        TActivitysStudents tActivitysStudents = new TActivitysStudents();
        tActivitysStudents.setStudentId(studentId);
        tActivitysStudents.setActivityId(activityId);
        return this.save(tActivitysStudents);
    }

    //取消活动
    @Override
    public int removeActivity(int activityId, int studentId) {
        return tActivitysStudentsMapper.removeActivityStudent(activityId, studentId);
    }
}
