package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.entityVo.TeacherQueryVo;
import com.tuoguan.tg.vod.service.TTeachersService;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.tgxt.utils.AuthContextHolder.getUserId;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-07
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TTeachersController {

    @Resource
    private TUsersService tUsersService;
    @Resource
    private TTeachersService teacherService;

    //http://localhost:8301/admin/vod/teacher/findAll
    //查询所有教师
    @GetMapping("teacher/findAll")
    public Result<List<TTeachers>> findAll() {
        return Result.ok(teacherService.list()).code(20000).message("查询讲师数据成功！");
    }

    //分页和条件查询教师
    @PostMapping("teacher/findQueryPage/{current}/{limit}")
    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
    public Result<IPage<TTeachers>> findPage(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        //创建page（分页）对象
        Page<TTeachers> pageParam = new Page<>(current, limit);
        if (teacherQueryVo == null) {//查询全部 没有条件
            IPage<TTeachers> pageModel = teacherService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            //获取条件值
            String teacherName = teacherQueryVo.getTeacherName();
            Integer level = teacherQueryVo.getLevel();
            String hireDateBegin = teacherQueryVo.getHireDateBegin();
            String hireDateEnd = teacherQueryVo.getHireDateEnd();
            //非空判断，条件封装
            QueryWrapper<TTeachers> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(teacherName)) {//判空
                wrapper.like("teacher_name", teacherName);
            }
            if (!StringUtils.isEmpty(level)) {//判空
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(hireDateBegin)) {//判空
                wrapper.ge("hire_date", hireDateBegin);//ge大于等于
            }
            if (!StringUtils.isEmpty(hireDateEnd)) {//判空
                wrapper.le("hire_date", hireDateEnd);//le小于等于
            }
            //调用方法得到分页查询结果
            IPage<TTeachers> pageModel = teacherService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //添加
    @PostMapping("teacher/saveTeacher")
    public Result<Object> saveTeacher(@RequestBody TTeachers teachers) {
        boolean isSuccess = teacherService.save(teachers);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("teacher/getTeacher/{id}")
    public Result<TTeachers> getTeacher(@PathVariable Long id) {
        TTeachers teachers = teacherService.getById(id);
        return Result.ok(teachers);
    }

    @PostMapping("teacher/updateTeacher")
    public Result<Object> updateById(@RequestBody TTeachers teachers) {
        teachers.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = teacherService.updateById(teachers);
        if (isSuccess) return Result.ok(null).code(20000).message("修改成功！");
        else return Result.fail(null).code(20001).message("修改失败！");
    }

    //逻辑删除
    @Transactional//spring统一管理事务
    @DeleteMapping("teacher/remove/{id}")
    public Result<Object> removeTeacher(@PathVariable Long id) {
        //先删除用户表的教师id
        // 使用UpdateWrapper来构造更新条件
        UpdateWrapper<TUsers> updateWrapper = new UpdateWrapper<>();
        final TTeachers tTeachers = teacherService.getById(id);
        final TUsers tUsers = tUsersService.getById(tTeachers.getUserId());
        if (tUsers != null) {
            updateWrapper.eq("id", tUsers.getId())
                    .set("teacher_id", null); // 指定teacher_id字段更新为null
            tUsersService.update(updateWrapper);
        }
        //最后删除教师
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) return Result.ok(null).code(20000).message("删除成功！");
        else return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @Transactional//spring统一管理事务
    @DeleteMapping("teacher/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList) {
        //先删除用户表的教师id
        // 使用UpdateWrapper来构造更新条件
        for (Long id : idList) {
            UpdateWrapper<TUsers> updateWrapper = new UpdateWrapper<>();
            final TTeachers tTeachers = teacherService.getById(id);
            final TUsers tUsers = tUsersService.getById(tTeachers.getUserId());
            if (tUsers != null) {
                updateWrapper.in("id", tUsers.getId())
                        .set("teacher_id", null); // 设置teacher_id为null
                tUsersService.update(updateWrapper);
            }
        }
        //最后删除教师
        boolean isSuccess = teacherService.removeByIds(idList);
        if (isSuccess) return Result.ok(null).code(20000).message("批量删除成功！");
        else return Result.fail(null).code(20001).message("批量删除失败！");
    }

    //绑定
    @PostMapping("teacher/saveTeacher/{id}")
    public Result<Object> bindTeacher(@PathVariable long id, @RequestBody TTeachers teachers) {
        boolean istTeacher = teacherService.save(teachers);
        Long teacherId = teachers.getId();

        //向用户表插入获取的教师id
        final TUsers tUsers = tUsersService.getById(id);
        tUsers.setTeacherId(Math.toIntExact(teacherId));
        tUsersService.updateById(tUsers);

        //更新教师表的用户id
        teachers.setUserId(Math.toIntExact(id));
        teacherService.updateById(teachers);
        if (istTeacher) return Result.ok(null).code(20000).message("绑定成功！");
        else return Result.fail(null).code(20001).message("绑定失败！");
    }

}

