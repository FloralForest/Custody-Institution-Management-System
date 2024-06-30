package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TClassify;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.mapper.TClassifyMapper;
import com.tuoguan.tg.vod.service.TClassifyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-16
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TClassifyController {

    @Resource
    private TClassifyService tClassifyService;
    //查询所有分类
    @GetMapping("classify/findAll")
    public Result<List<TClassify>> findAll() {
        return Result.ok(tClassifyService.list()).code(20000).message("查询分类数据成功！");
    }
    @GetMapping("classify/courseClassify/{id}")
    public Result<List<TClassify>> getClassify(@PathVariable Long id) {
        TClassify classify = tClassifyService.getById(id);
        return Result.ok(Collections.singletonList(classify));
    }
    //添加
    @PostMapping("classify/saveClassify")
    public Result<Object> saveUser(@RequestBody TClassify tClassify){
        boolean isSuccess = tClassifyService.save(tClassify);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }
    

    //删除
    @DeleteMapping("classify/remove/{id}")
    public Result<Object> removeUser(@PathVariable Long id){
        boolean isSuccess = tClassifyService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //导出
    @GetMapping(value = "exportData")
    public void exportData(HttpServletResponse response) {
        tClassifyService.exportData(response);
    }

    //导入
    @PostMapping("importData")
    public Result<Object> importData(MultipartFile file) {
        tClassifyService.importDictData(file);
        return Result.ok(null);
    }
}

