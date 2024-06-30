package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tuoguan.tg.vod.entity.TCoursesStudents;
import com.tuoguan.tg.vod.mapper.TCoursesMapper;
import com.tuoguan.tg.vod.mapper.TCoursesStudentsMapper;
import com.tuoguan.tg.vod.mapper.TStudentsMapper;
import com.tuoguan.tg.vod.service.TCoursesStudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程学生关联表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-17
 */
@Service
public class TCoursesStudentsServiceImpl extends ServiceImpl<TCoursesStudentsMapper, TCoursesStudents> implements TCoursesStudentsService {

    @Resource
    private TCoursesMapper tCoursesMapper;

    @Resource
    private TStudentsMapper tStudentsMapper;

    @Resource
    private TCoursesStudentsMapper tCoursesStudentsMapper;

    @Override
    public boolean selectCourse(int studentId, int courseId) {
        // 检查学生和课程是否存在
        if (tStudentsMapper.selectById(studentId) == null || tCoursesMapper.selectById(courseId) == null) {
            return false;
        }
        // 检查是否已经选过这门课
        LambdaQueryWrapper<TCoursesStudents> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TCoursesStudents::getStudentId, studentId).eq(TCoursesStudents::getCourseId, courseId);
        if (this.count(lambdaQueryWrapper) > 0) {
            return false;
        }
        // 添加选课记录
        TCoursesStudents tCoursesStudents = new TCoursesStudents();
        tCoursesStudents.setStudentId(studentId);
        tCoursesStudents.setCourseId(courseId);
        return this.save(tCoursesStudents);
    }

    //取消选课
    @Override
    public int removeCourse(int courseId, int studentId) {
        return tCoursesStudentsMapper.removeCourseStudent(courseId, studentId);
    }
}
