package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.THealth;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.entityVo.HealthQueryVo;
import com.tuoguan.tg.vod.entity.entityVo.TeacherQueryVo;
import com.tuoguan.tg.vod.service.THealthService;
import com.tuoguan.tg.vod.service.TStudentsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 学生健康情况表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-24
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class THealthController {

    @Resource
    private TStudentsService tStudentsService;

    @Resource
    private THealthService tHealthService;

    //分页和条件查询
    @PostMapping("health/findQueryPage/{page}/{limit}")
    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
    public Result<IPage<THealth>> findPage(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) HealthQueryVo healthQueryVo) {
        //创建page（分页）对象
        Page<THealth> pageParam = new Page<>(page, limit);
        if (healthQueryVo == null) {//查询全部 没有条件
            IPage<THealth> pageModel = tHealthService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            //获取条件值
            String studentName = healthQueryVo.getStudentName();
            //非空判断，条件封装
            LambdaQueryWrapper<THealth> wrapper = new LambdaQueryWrapper<>();
            if (!StringUtils.isEmpty(studentName)) {//判空
                wrapper.like(THealth::getStudentName, studentName);
            }
            //调用方法得到分页查询结果
            IPage<THealth> pageModel = tHealthService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //添加
    @PostMapping("health/saveHealth")
    public Result<Object> saveHealth(@RequestBody THealth tHealth) {
        boolean isSuccess = tHealthService.save(tHealth);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("health/getHealth/{id}")
    public Result<THealth> getHealth(@PathVariable Long id) {
        THealth tHealth = tHealthService.getById(id);
        return Result.ok(tHealth);
    }

    @PostMapping("health/updateHealth")
    public Result<Object> updateById(@RequestBody THealth tHealth) {
        boolean isSuccess = tHealthService.updateById(tHealth);
        if (isSuccess) return Result.ok(null).code(20000).message("修改成功！");
        else return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除
    @Transactional//spring统一管理事务
    @DeleteMapping("health/remove/{id}")
    public Result<Object> removeHealth(@PathVariable Long id) {
        //删除
        boolean isSuccess = tHealthService.removeById(id);
        if (isSuccess) return Result.ok(null).code(20000).message("删除成功！");
        else return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("health/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList){
        boolean isSuccess = tHealthService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }

    //根据学生id查询
    @GetMapping("health/getStudentHealth/{studentId}")
    public Result<THealth> getStudentHealth(@PathVariable Long studentId){
        THealth tHealth = tHealthService.getStudentId(studentId);
        return Result.ok(tHealth);
    }
}

