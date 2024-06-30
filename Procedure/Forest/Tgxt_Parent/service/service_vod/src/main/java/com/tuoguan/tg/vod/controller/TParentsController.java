package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.*;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;
import com.tuoguan.tg.vod.entity.entityVo.TeacherQueryVo;
import com.tuoguan.tg.vod.service.TParentsService;
import com.tuoguan.tg.vod.service.TParentsStudentsService;
import com.tuoguan.tg.vod.service.TStudentsService;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 家长表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-10
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TParentsController {

    @Resource
    private TUsersService tUsersService;
    @Resource
    private TParentsService parentsService;
    @Resource
    private TStudentsService studentsService;
    @Resource
    private TParentsStudentsService parentsStudentsService;

    //查询所有家长
    @GetMapping("parent/findAll")
    public Result<List<TParents>> findAll() {
        return Result.ok(parentsService.list()).code(20000).message("查询家长数据成功！");
    }

    //分页和条件查询家长
    @PostMapping("parent/findQueryPage/{current}/{limit}")
    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
    public Result<IPage<TParents>> findPage(@PathVariable long current,
                                            @PathVariable long limit,
                                            @RequestBody(required = false) TParents tParents) {
        //创建page（分页）对象
        Page<TParents> pageParam = new Page<>(current, limit);
        if (tParents == null) {//查询全部 没有条件
            IPage<TParents> pageModel = parentsService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            //获取条件值
            String parentName = tParents.getParentName();
            //非空判断，条件封装
            QueryWrapper<TParents> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(parentName)) {//判空
                wrapper.like("parent_name", parentName);
            }
            //调用方法得到分页查询结果
            IPage<TParents> pageModel = parentsService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //添加
    @PostMapping("parent/saveParent")
    public Result<Object> saveParent(@RequestBody TParents parents) {
        boolean isSuccess = parentsService.save(parents);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("parent/getParent/{id}")
    public Result<TParents> getParent(@PathVariable Long id) {
        TParents parents = parentsService.getById(id);
        return Result.ok(parents);
    }

    @PostMapping("parent/updateParent")
    public Result<Object> updateById(@RequestBody TParents parents) {
        parents.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = parentsService.updateById(parents);
        if (isSuccess)
            return Result.ok(null).code(20000).message("修改成功！");
        else
            return Result.fail(null).code(20001).message("修改失败！");
    }

    //逻辑删除
    @Transactional//spring统一管理事务
    @DeleteMapping("parent/remove/{id}")
    public Result<Object> removeParent(@PathVariable Long id) {
        if (id != null) {
            //先将用户表的家长id制空
            //使用UpdateWrapper来构造更新条件
            UpdateWrapper<TUsers> updateWrapper = new UpdateWrapper<>();
            final TParents tParents = parentsService.getById(id);
            final TUsers tUsers = tUsersService.getById(tParents.getUserId());
            if (tUsers != null) {
                updateWrapper.eq("id", tUsers.getId())
                        .set("parent_id", null); // 指定parent_id字段更新为null
                tUsersService.update(updateWrapper);
            }
            final LambdaQueryWrapper<TParentsStudents> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //与QueryWrapper相比不需要写表字段名
            lambdaQueryWrapper.eq(TParentsStudents::getParentId, id);
            //查找与id关联的子女删除
            List<TParentsStudents> parentsStudents = parentsStudentsService.list(lambdaQueryWrapper);
            parentsStudents.forEach(parentStudent -> {
                studentsService.removeById(parentStudent.getStudentId());
            });

            //再删除家长子女关联表
            parentsStudentsService.remove(lambdaQueryWrapper);

            //最后删除家长
            boolean isSuccess = parentsService.removeById(id);
            if (isSuccess)
                return Result.ok(null).code(20000).message("删除成功！");
            else
                return Result.fail(null).code(20001).message("删除失败！");
        } else {
            return Result.fail(null).code(20001).message("删除失败！");
        }
    }

    //批量删除
    @Transactional//spring统一管理事务
    @DeleteMapping("parent/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList) {
        //先删除用户表的教师id
        // 使用UpdateWrapper来构造更新条件
        if (idList != null) {
            for (Long id : idList) {
                UpdateWrapper<TUsers> updateWrapper = new UpdateWrapper<>();
                final TParents tParents = parentsService.getById(id);
                final TUsers tUsers = tUsersService.getById(tParents.getUserId());
                if (tUsers != null) {
                    updateWrapper.in("id", tUsers.getId())
                            .set("parent_id", null); // 设置parent_id为null
                    tUsersService.update(updateWrapper);
                }

                final QueryWrapper<TParentsStudents> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("parent_id", id);
                //查找与id关联的子女删除
                List<TParentsStudents> parentsStudents = parentsStudentsService.list(queryWrapper);
                parentsStudents.forEach(parentStudent -> {
                    studentsService.removeById(parentStudent.getStudentId());
                });
                //再删除家长子女关联表
                parentsStudentsService.remove(queryWrapper);
            }
            //删除家长
            boolean isSuccess = parentsService.removeByIds(idList);
            if (isSuccess)
                return Result.ok(null).code(20000).message("批量删除成功！");
            else
                return Result.fail(null).code(20001).message("批量删除失败！");
        } else {
            return Result.fail(null).code(20001).message("批量删除失败！");
        }
    }

    //绑定
    @PostMapping("parent/saveParent/{id}")
    public Result<Object> bindParent(@PathVariable long id,
                                     @RequestBody TParents parents) {
        boolean isParent = parentsService.save(parents);
        if (id != 0) {
            Long parentsId = parents.getId();

            //向用户表插入获取的家长id
            final TUsers tUsers = tUsersService.getById(id);
            tUsers.setParentId(Math.toIntExact(parentsId));
            tUsersService.updateById(tUsers);

            //更新家长表的用户id
            parents.setUserId(Math.toIntExact(id));
            parentsService.updateById(parents);
        }
        if (isParent)
            return Result.ok(null).code(20000).message("绑定成功！");
        else
            return Result.fail(null).code(20001).message("绑定失败！");
    }

    //查询所有用户以及关联的信息
    @GetMapping("parent/getChildren/{id}")
    public Result<List<ParentStudentDTO>> getChildren(@PathVariable Long id) {
        final List<ParentStudentDTO> parentStudent = parentsService.selectParentStudent(id);
        return Result.ok(parentStudent);
    }

}

