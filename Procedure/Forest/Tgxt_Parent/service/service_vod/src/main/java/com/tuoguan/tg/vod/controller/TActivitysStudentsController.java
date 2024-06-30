package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.service.TActivitysStudentsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 活动学生关联表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TActivitysStudentsController {
    @Resource
    private TActivitysStudentsService tActivitysStudentsService;

    //选活动  @RequestParam用于将请求参数区域的数据映射到控制层方法的参数上
    @PostMapping("students/{studentId}/activity/{activityId}")
    public Result<Object> selectActivity(@PathVariable int studentId, @PathVariable int activityId) {

        boolean success = tActivitysStudentsService.selectActivity(studentId, activityId);
        if (success) {
            return Result.ok(null).code(20000).message("选择成功！");
        } else {
            return Result.fail(null).code(20001).message("选择失败.检查是否已选！");
        }
    }

    //取消活动
    @DeleteMapping("remove/activity/{activityId}/student/{studentId}")
    public Result<Object> removeActivity(@PathVariable int activityId, @PathVariable int studentId) {

        int success = tActivitysStudentsService.removeActivity(activityId, studentId);
        if (success > 0) {
            return Result.ok(null).code(20000).message("取消成功！");
        } else {
            return Result.fail(null).code(20001).message("取消失败.请联系管理员！");
        }
    }

}

