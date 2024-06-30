package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TTasks;
import com.tuoguan.tg.vod.entity.TTeacherEvaluate;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.entityDTO.StudentTaskDTO;
import com.tuoguan.tg.vod.entity.entityDTO.TeacherEvaluateDTO;
import com.tuoguan.tg.vod.service.TTasksService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作业辅导 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-08
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TTasksController {
    @Resource
    private TTasksService tTasksService;

    //查询选择指定学生的全部作业信息
    @GetMapping("studentTask/{id}")
    public Result<Map<String, Object>> studentTaskAll(@PathVariable long id) {

        final List<StudentTaskDTO> studentTaskList = tTasksService.studentTask(id);
        final Map<String, Object> map = new HashMap<>();
        map.put("studentTaskList", studentTaskList);
        return Result.ok(map).code(20000).message("查询数据成功！");
    }

    //添加
    @PostMapping("studentTask/saveTask")
    public Result<Object> saveTask(@RequestBody TTasks tTasks) {
        boolean isSuccess = tTasksService.save(tTasks);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("studentTask/getTask/{id}")
    public Result<TTasks> getTask(@PathVariable Long id) {
        TTasks tTasks = tTasksService.getById(id);
        return Result.ok(tTasks);
    }

    @PostMapping("studentTask/updateTask")
    public Result<Object> updateById(@RequestBody TTasks tTasks) {
        boolean isSuccess = tTasksService.updateById(tTasks);
        if (isSuccess) return Result.ok(null).code(20000).message("修改成功！");
        else return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除
    @Transactional//spring统一管理事务
    @DeleteMapping("studentTask/removeTask/{id}")
    public Result<Object> removeTask(@PathVariable Long id) {
        //删除
        boolean isSuccess = tTasksService.removeById(id);
        if (isSuccess) return Result.ok(null).code(20000).message("删除成功！");
        else return Result.fail(null).code(20001).message("删除失败！");
    }


}

