package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TActivitys;
import com.tuoguan.tg.vod.entity.entityDTO.ActivityStudentDTO;
import com.tuoguan.tg.vod.entity.entityVo.ActivityTeacherVO;
import com.tuoguan.tg.vod.service.TActivitysService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表	 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TActivityController {

    @Resource
    private TActivitysService tActivitysService;

    //查询所有
    @GetMapping("activity/findAll")
    public Result<List<TActivitys>> activityFindAll() {
        return Result.ok(tActivitysService.list()).code(20000).message("查询活动数据成功！");
    }

    //id查询（数组）
    @GetMapping("activity/activityArray/{id}")
    public Result<List<TActivitys>> activityArray(@PathVariable Long id) {
        TActivitys tActivitys = tActivitysService.getById(id);
        return Result.ok(Collections.singletonList(tActivitys));//返回数组
    }

    //查询选择指定活动的全部学生
    @GetMapping("activityChart/{id}")
    public Result<Map<String, Object>> activityChart(@PathVariable long id) {

        final List<ActivityStudentDTO> activityStudent = tActivitysService.selectActivityStudent(id);
        int total = activityStudent.size();
        final Map<String, Object> map = new HashMap<>();
        map.put("activityStudentDTOList",activityStudent);
        map.put("total",total);
        return Result.ok(map).code(20000).message("查询课程数据成功！");
    }

    //当前页，每页记录数以及获取前端传来的查询条件查询对象
    @GetMapping("activity/findQueryPage/{page}/{limit}")
    public Result
            <Map<String, Object>> activityList(@PathVariable Long page,
                                             @PathVariable Long limit,
                                             ActivityTeacherVO activityTeacherVO) {
        Page<TActivitys> pageParam = new Page<>(page, limit);
        Map<String, Object> map = tActivitysService.findPageActivity(pageParam, activityTeacherVO);
        return Result.ok(map);
    }

    //添加
    @PostMapping("activity/saveActivity")
    public Result<Object> saveActivity(@RequestBody TActivitys tActivitys) {
        boolean isSuccess = tActivitysService.save(tActivitys);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("activity/getActivity/{id}")
    public Result<TActivitys> getActivity(@PathVariable Long id) {
        TActivitys tActivitys = tActivitysService.getById(id);
        return Result.ok(tActivitys);
    }

    @PostMapping("activity/updateActivity")
    public Result<Object> updateById(@RequestBody TActivitys tActivitys) {
        tActivitys.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = tActivitysService.updateById(tActivitys);
        if (isSuccess)
            return Result.ok(null).code(20000).message("修改成功！");
        else
            return Result.fail(null).code(20001).message("修改失败！");
    }

    //逻辑删除
    @DeleteMapping("activity/remove/{id}")
    public Result<Object> removeActivity(@PathVariable Long id){
        boolean isSuccess = tActivitysService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("activity/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList){
        boolean isSuccess = tActivitysService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }
}

