package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TCourseEvaluate;
import com.tuoguan.tg.vod.entity.TTeacherEvaluate;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.entityDTO.CourseEvaluateDTO;
import com.tuoguan.tg.vod.entity.entityDTO.TeacherEvaluateDTO;
import com.tuoguan.tg.vod.service.TTeacherEvaluateService;
import com.tuoguan.tg.vod.service.TTeachersService;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 教师评价 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-27
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TTeacherEvaluateController {

    @Resource
    private TTeacherEvaluateService tTeacherEvaluateService;

    @Resource
    private TTeachersService teachersService;

    @Resource
    private TUsersService tUsersService;

    //查询选择指定教师的全部评论
    @GetMapping("activityTeacherEvaluate/{id}")
    public Result<Map<String, Object>> activityChartEvaluateAll(@PathVariable long id) {

        final List<TeacherEvaluateDTO> teacherEvaluateDTOList = teachersService.selectEvaluate(id);
        final Map<String, Object> map = new HashMap<>();
        map.put("teacherEvaluateDTOList", teacherEvaluateDTOList);
        return Result.ok(map).code(20000).message("查询数据成功！");
    }

    //添加
    @PostMapping("teacher/saveEvaluate")
    public Result<Object> saveEvaluate(@RequestBody TTeacherEvaluate tTeacherEvaluate) {
        final TUsers tUsers = tUsersService.getById(tTeacherEvaluate.getPeopleId());
        final TTeachers tTeachers = teachersService.getById(tUsers.getTeacherId());
        if (tTeachers != null) {
            if (Objects.equals(tTeacherEvaluate.getTeacherId().longValue(), tTeachers.getId())) {
                return Result.fail(null).code(20001).message("不能给自己添加评论");
            }
        }

        if (tTeacherEvaluate.getFraction() == 0 ||
                tTeacherEvaluate.getContent() == null || tTeacherEvaluate.getContent().trim().isEmpty()) {
            return Result.fail(null).code(20001).message("评分或内容不能为空");
        }
        boolean isSuccess = tTeacherEvaluateService.save(tTeacherEvaluate);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("teacher/getEvaluate/{id}")
    public Result<TTeacherEvaluate> getEvaluate(@PathVariable Long id) {
        TTeacherEvaluate tTeacherEvaluate = tTeacherEvaluateService.getById(id);
        return Result.ok(tTeacherEvaluate);
    }

    @PostMapping("teacher/updateEvaluate")
    public Result<Object> updateById(@RequestBody TTeacherEvaluate tTeacherEvaluate) {
        boolean isSuccess = tTeacherEvaluateService.updateById(tTeacherEvaluate);
        if (isSuccess) return Result.ok(null).code(20000).message("修改成功！");
        else return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除
    @Transactional//spring统一管理事务
    @DeleteMapping("teacher/removeEvaluate/{id}")
    public Result<Object> removeEvaluate(@PathVariable Long id) {
        //删除
        boolean isSuccess = tTeacherEvaluateService.removeById(id);
        if (isSuccess) return Result.ok(null).code(20000).message("删除成功！");
        else return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("teacher/removeBatchEvaluate")
    public Result<Object> removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = tTeacherEvaluateService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }

}

