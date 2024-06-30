package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.CurrentUser;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.TProxys;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;
import com.tuoguan.tg.vod.entity.entityVo.CourseTeacherVO;
import com.tuoguan.tg.vod.service.TCoursesService;
import com.tuoguan.tg.vod.service.TProxysService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程表	 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-15
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TCoursesController {

    @Resource
    private TCoursesService coursesService;

    //代课
    @Resource
    private TProxysService tProxysService;

    //查询所有课程
    @GetMapping("course/findAll")
    public Result<List<TCourses>> courseFindAll() {
        return Result.ok(coursesService.list()).code(20000).message("查询课程数据成功！");
    }

    //id查询课程（数组）
    @GetMapping("course/courseArray/{id}")
    public Result<List<TCourses>> courseArray(@PathVariable Long id) {
        TCourses courses = coursesService.getById(id);
        return Result.ok(Collections.singletonList(courses));
    }

    //查询选择指定课程的全部学生
    @GetMapping("chart/{id}")
    public Result<Map<String, Object>> chartFindAll(@PathVariable long id) {

        final List<CourseStudentDTO> courseStudentDTOList = coursesService.selectCourseStudent(id);
        int total = courseStudentDTOList.size();
        final Map<String, Object> map = new HashMap<>();
        map.put("courseStudentDTOList", courseStudentDTOList);
        map.put("total", total);
        return Result.ok(map).code(20000).message("查询课程数据成功！");
    }

    //当前页，每页记录数以及获取前端传来的查询条件查询对象
    @GetMapping("course/findQueryPage/{page}/{limit}")
    public Result
            <Map<String, Object>> courseList(@PathVariable Long page,
                                             @PathVariable Long limit,
                                             CourseTeacherVO courseTeacherVO) {
        Page<TCourses> pageParam = new Page<>(page, limit);
        Map<String, Object> map = coursesService.findPageCourse(pageParam, courseTeacherVO);
        return Result.ok(map);
    }

    //添加
    @PostMapping("course/saveCourse")
    public Result<Object> saveCourse(@RequestBody TCourses courses) {
        boolean isSuccess = coursesService.save(courses);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("course/getCourse/{id}")
    public Result<TCourses> getCourse(@PathVariable Long id) {
        TCourses courses = coursesService.getById(id);
        return Result.ok(courses);
    }

    @PostMapping("course/updateCourse")
    public Result<Object> updateById(@RequestBody TCourses courses) {
        courses.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = coursesService.updateById(courses);
        if (isSuccess)
            return Result.ok(null).code(20000).message("修改成功！");
        else
            return Result.fail(null).code(20001).message("修改失败！");
    }

    //逻辑删除
    @DeleteMapping("course/remove/{id}")
    public Result<Object> removeCourse(@PathVariable Long id) {
        boolean isSuccess = coursesService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //代课
    @PostMapping("proxy/saveProxy/{id}")
    public Result<Object> saveProxy(@PathVariable Long id,
                                    CurrentUser currentUser) {
        // 判空
        if (currentUser.getTeacherId() != null) {
            //;获取课程教师id信息与当前的登录教师信息id对比
            if (!coursesService.getById(id).getTeacherId().equals(currentUser.getTeacherId())){
                final TProxys tProxys = new TProxys();
                tProxys.setCourseId(Math.toIntExact(id));
                tProxys.setTeacherId(currentUser.getTeacherId());
                boolean isSuccess = tProxysService.saveProxy(tProxys);
                if (isSuccess)
                    return Result.ok(null).code(20000).message("添加成功！");
                else
                    return Result.fail(null).code(20001).message("添加失败！不能重复操作");
            }else {
                return Result.fail(null).code(20001).message("添加失败！不能为自己代课");
            }
        } else {
            return Result.fail(null).code(20001).message("该账号未绑定教师信息！不可为他人代课");
        }
    }
}

