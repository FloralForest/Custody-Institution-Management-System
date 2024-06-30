package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.TProxys;
import com.tuoguan.tg.vod.entity.TStudents;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.service.TParentsService;
import com.tuoguan.tg.vod.service.TProxysService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 代课表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-02
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TProxysController {
    @Resource
    private TProxysService tProxysService;

    //根据课程id查询代理教师信息（数组）
    @GetMapping("proxy/courseProxy/{id}")
    public Result<TTeachers> courseProxy(@PathVariable Long id) {
        TTeachers teachers = tProxysService.getCourseId(id);
        return Result.ok(teachers);
    }

    //取消代课
    @DeleteMapping("proxy/remove/{id}")
    public Result<Object> removeProxy(@PathVariable Long id){
        boolean isSuccess = tProxysService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("取消成功！");
        else
            return Result.fail(null).code(20001).message("取消失败！");
    }
}

