package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.THealth;
import com.tuoguan.tg.vod.entity.TKnowledge;
import com.tuoguan.tg.vod.entity.entityVo.HealthQueryVo;
import com.tuoguan.tg.vod.service.TKnowledgeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 健康知识表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-25
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TKnowledgeController {

    @Resource
    private TKnowledgeService tKnowledgeService;

    //查询所有
    @GetMapping("knowledge/findAll")
    public Result<List<TKnowledge>> knowledgeFindAll() {
        return Result.ok(tKnowledgeService.list()).code(20000).message("查询数据成功！");
    }

    //分页和条件查询
    @PostMapping("knowledge/findQueryPage/{page}/{limit}")
    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
    public Result<IPage<TKnowledge>> findPage(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) TKnowledge tKnowledge) {
        //创建page（分页）对象
        Page<TKnowledge> pageParam = new Page<>(page, limit);
        if (tKnowledge == null) {//查询全部 没有条件
            IPage<TKnowledge> pageModel = tKnowledgeService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            //获取条件值
            String title = tKnowledge.getKnowledgeTitle();
            //非空判断，条件封装
            LambdaQueryWrapper<TKnowledge> wrapper = new LambdaQueryWrapper<>();
            if (!StringUtils.isEmpty(title)) {//判空
                wrapper.like(TKnowledge::getKnowledgeTitle, title);
            }
            //调用方法得到分页查询结果
            IPage<TKnowledge> pageModel = tKnowledgeService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //添加
    @PostMapping("knowledge/saveKnowledge")
    public Result<Object> saveKnowledge(@RequestBody TKnowledge tKnowledge) {
        if (tKnowledge.getKnowledgeTitle() == null || tKnowledge.getKnowledgeTitle().trim().isEmpty() ||
                tKnowledge.getKnowledgeContent() == null || tKnowledge.getKnowledgeContent().trim().isEmpty()) {
            return Result.fail(null).code(20001).message("标题或内容不能为空");
        }
        boolean isSuccess = tKnowledgeService.save(tKnowledge);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("knowledge/getKnowledge/{id}")
    public Result<TKnowledge> getKnowledge(@PathVariable Long id) {
        TKnowledge tKnowledge = tKnowledgeService.getById(id);
        return Result.ok(tKnowledge);
    }

    @PostMapping("knowledge/updateKnowledge")
    public Result<Object> updateById(@RequestBody TKnowledge tKnowledge) {
        tKnowledge.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = tKnowledgeService.updateById(tKnowledge);
        if (isSuccess) return Result.ok(null).code(20000).message("修改成功！");
        else return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除
    @Transactional//spring统一管理事务
    @DeleteMapping("knowledge/remove/{id}")
    public Result<Object> removeKnowledge(@PathVariable Long id) {
        //删除
        boolean isSuccess = tKnowledgeService.removeById(id);
        if (isSuccess) return Result.ok(null).code(20000).message("删除成功！");
        else return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("knowledge/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList){
        boolean isSuccess = tKnowledgeService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }

}

