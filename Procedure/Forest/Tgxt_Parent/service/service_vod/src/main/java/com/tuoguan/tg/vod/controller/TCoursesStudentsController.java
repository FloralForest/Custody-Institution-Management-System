package com.tuoguan.tg.vod.controller;

import com.tgxt.result.Result;
import com.tuoguan.tg.vod.service.TCoursesService;
import com.tuoguan.tg.vod.service.TCoursesStudentsService;
import com.tuoguan.tg.vod.service.TStudentsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TCoursesStudentsController {

    @Resource
    private TCoursesStudentsService tCoursesStudentsService;

    //选课  @RequestParam用于将请求参数区域的数据映射到控制层方法的参数上
    @PostMapping("students/{studentId}/courses/{courseId}")
    public Result<Object> selectCourse(@PathVariable int studentId, @PathVariable int courseId) {

        boolean success = tCoursesStudentsService.selectCourse(studentId, courseId);
        if (success) {
            return Result.ok(null).code(20000).message("选课成功！");
        } else {
            return Result.fail(null).code(20001).message("选课失败.检查是否已选！");
        }
    }

    //取消选课
    @DeleteMapping("remove/courses/{courseId}/student/{studentId}")
    public Result<Object> removeCourse(@PathVariable int courseId, @PathVariable int studentId) {

        int success = tCoursesStudentsService.removeCourse(courseId, studentId);
        if (success > 0) {
            return Result.ok(null).code(20000).message("取消成功！");
        } else {
            return Result.fail(null).code(20001).message("取消失败.请联系管理员！");
        }
    }
}
