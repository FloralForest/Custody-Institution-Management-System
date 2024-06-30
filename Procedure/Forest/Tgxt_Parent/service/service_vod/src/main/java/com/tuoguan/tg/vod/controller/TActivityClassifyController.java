package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TActivityClassify;
import com.tuoguan.tg.vod.entity.TClassify;
import com.tuoguan.tg.vod.service.TActivityClassifyService;
import com.tuoguan.tg.vod.service.TActivitysStudentsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程分类表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TActivityClassifyController {

    @Resource
    private TActivityClassifyService tActivityClassifyService;

    //查询所有分类
    @GetMapping("activityClassify/findAll")
    public Result<List<TActivityClassify>> findAll() {
        return Result.ok(tActivityClassifyService.list()).code(20000).message("查询分类数据成功！");
    }
    @GetMapping("activity/activityClassify/{id}")
    public Result<TActivityClassify> getClassify(@PathVariable Long id) {
        TActivityClassify tActivityClassify = tActivityClassifyService.getById(id);
        return Result.ok(tActivityClassify);
    }

    //添加
    @PostMapping("activityClassify/saveClassify")
    public Result<Object> saveUser(@RequestBody TActivityClassify tActivityClassify){
        boolean isSuccess = tActivityClassifyService.save(tActivityClassify);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }


    //删除
    @DeleteMapping("activityClassify/remove/{id}")
    public Result<Object> removeUser(@PathVariable Long id){
        boolean isSuccess = tActivityClassifyService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //导出
    @GetMapping(value = "activityExportData")
    public void exportData(HttpServletResponse response) {
        tActivityClassifyService.exportData(response);
    }

    //导入
    @PostMapping("activityImportData")
    public Result<Object> importData(MultipartFile file) {
        tActivityClassifyService.importDictData(file);
        return Result.ok(null);
    }
}

