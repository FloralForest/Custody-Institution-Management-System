package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.TClassify;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.entityDTO.CourseEvaluateDTO;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityVo.CourseTeacherVO;
import com.tuoguan.tg.vod.mapper.TCoursesMapper;
import com.tuoguan.tg.vod.mapper.TStudentsMapper;
import com.tuoguan.tg.vod.service.TClassifyService;
import com.tuoguan.tg.vod.service.TCoursesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuoguan.tg.vod.service.TTeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 课程表	 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-15
 */
@Service
public class TCoursesServiceImpl extends ServiceImpl<TCoursesMapper, TCourses> implements TCoursesService {

    @Resource
    private TClassifyService tClassifyService;
    @Resource
    private TTeachersService teachersService;

    @Resource
    private TCoursesMapper tCoursesMapper;

    //查询
    @Override
    public Map<String, Object> findPageCourse(Page<TCourses> pageParam, CourseTeacherVO courseTeacherVO) {
        //获取条件值
        String courseName = courseTeacherVO.getCourseName();
        Integer classifyId = courseTeacherVO.getClassifyId();
        Integer teacherId = courseTeacherVO.getTeacherId();

        //封装条件
        //使用QueryWrapper时需要写数据库字段例如：wrapper.eq("parent_id",id);
        LambdaQueryWrapper<TCourses> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(courseName)) {
            wrapper.like(TCourses::getCourseName, courseName);
        }
        if (!StringUtils.isEmpty(classifyId)) {
            wrapper.eq(TCourses::getClassifyId, classifyId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq(TCourses::getTeacherId, teacherId);
        }
        //调用方法查询(进行条件查询带分页)
        Page<TCourses> page = baseMapper.selectPage(pageParam, wrapper);
        long total = page.getTotal();//总记录数
        long totalPage = page.getPages();//总页数
        long currentPage = page.getCurrent();//当前页
        long size = page.getSize();//每页记录数
        List<TCourses> records = page.getRecords();//每页数据集合
        //遍历封装教师和分类名称(根据id查询名称最终显示)
//        records.stream().forEach(flor ->{
//            this.getNameById(flor);
//        });
        records.forEach(this::getNameById);

        //封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", total);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }

    //获取教师和分类名称
    private void getNameById(TCourses tCourses) {
        //查询教师名称
        TTeachers teacher = teachersService.getById(tCourses.getTeacherId());
        if (teacher != null) {
            tCourses.getParam().put("teacherName", teacher.getTeacherName());
            tCourses.getParam().put("level", teacher.getLevel());
            tCourses.getParam().put("courseMajor", teacher.getTeachersAvatar());
        }

        //查询分类名称
        TClassify classify = tClassifyService.getById(tCourses.getClassifyId());
        if (classify != null) {
            tCourses.getParam().put("classifyName", classify.getClassifyName());
        }
    }



    //查询选择该门课学生
    @Override
    public List<CourseStudentDTO> selectCourseStudent(long id) {
        return tCoursesMapper.selectCourseStudent(id);
    }

    @Override
    public List<CourseEvaluateDTO> selectEvaluate(long id) {
        return tCoursesMapper.selectEvaluate(id);
    }
}
