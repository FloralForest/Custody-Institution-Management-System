package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.mapper.TUsersMapper;
import com.tuoguan.tg.vod.service.TTeachersService;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表	 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-08
 */
@RestController
@RequestMapping("/admin/vod/")
@CrossOrigin //解决跨域
public class TUsersController {

    @Resource
    private TUsersService tUsersService;

    //查询所有用户以及关联的信息
    @GetMapping("user/findAll")
    public Result<List<TUsers>> findAll() {
        return Result.ok(tUsersService.selectUsersTeachers()).code(20000).message("查询用户数据成功！");
    }

    //分页和条件查询用户
    @PostMapping("user/findQueryPage/{current}/{limit}")
    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
    public Result<IPage<TUsers>> findPage(@PathVariable long current,
                                             @PathVariable long limit,
                                             @RequestBody(required = false) TUsers tUsers) {
        //创建page（分页）对象
        Page<TUsers> pageParam = new Page<>(current, limit);
        if (tUsers == null) {//查询全部 没有条件
            IPage<TUsers> pageModel = tUsersService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            //获取条件值
            String userName = tUsers.getUsername();
            Integer role = tUsers.getRole();
            //非空判断，条件封装
            QueryWrapper<TUsers> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(userName)) {//判空
                wrapper.like("username", userName);
            }
            if (!StringUtils.isEmpty(role)) {//判空
                wrapper.eq("role", role);
            }
            //调用方法得到分页查询结果
            IPage<TUsers> pageModel = tUsersService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //添加
    @PostMapping("user/saveUser")
    public Result<Object> saveUser(@RequestBody TUsers users){
        final LambdaQueryWrapper<TUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TUsers::getUsername, users.getUsername());
        TUsers existingUser = tUsersService.getOne(wrapper);
        if (existingUser != null) {
            return Result.fail(null).code(20001).message("用户已存在，请更换用户名");
        }
        boolean isSuccess = tUsersService.save(users);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("user/getUser/{id}")
    public Result<TUsers> getUser(@PathVariable Long id){
        TUsers users = tUsersService.getById(id);
        return Result.ok(users);
    }
    @PostMapping("user/updateUser")
    public Result<Object> updateById(@RequestBody TUsers users) {

        final LambdaQueryWrapper<TUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TUsers::getUsername, users.getUsername());
        TUsers existingUser = tUsersService.getOne(wrapper);
        if (existingUser != null && !Objects.equals(existingUser.getId(), users.getId())) {//名字一样但是两个id不相等可以添加
            return Result.fail(null).code(20001).message("用户已存在，请更换用户名");
        } else {
            users.setUpdateTime(LocalDateTime.now());
            boolean isSuccess = tUsersService.updateById(users);
            if (isSuccess)
                return Result.ok(null).code(20000).message("修改成功！");
            else
                return Result.fail(null).code(20001).message("修改失败！");
        }
    }

    //逻辑删除
    @DeleteMapping("user/remove/{id}")
    public Result<Object> removeUser(@PathVariable Long id){
        boolean isSuccess = tUsersService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("user/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList){
        boolean isSuccess = tUsersService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }
}

