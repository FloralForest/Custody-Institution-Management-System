package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TActivitys;
import com.tuoguan.tg.vod.entity.TRecipes;
import com.tuoguan.tg.vod.entity.entityVo.ActivityTeacherVO;
import com.tuoguan.tg.vod.entity.entityVo.RecipesQueryVo;
import com.tuoguan.tg.vod.service.TRecipesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 每日食谱表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-07
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TRecipesController {

    @Resource
    private TRecipesService tRecipesService;

    //查询所有
    @GetMapping("recipe/findAll")
    public Result<List<TRecipes>> recipeFindAll() {
        return Result.ok(tRecipesService.list()).code(20000).message("查询数据成功！");
    }

    //当前页，每页记录数以及获取前端传来的查询条件查询对象
    @GetMapping("recipe/findQueryPage/{page}/{limit}")
    public Result
            <Map<String, Object>> recipeList(@PathVariable Long page,
                                               @PathVariable Long limit,
                                               RecipesQueryVo recipesQueryVo) {
        Page<TRecipes> pageParam = new Page<>(page, limit);
        Map<String, Object> map = tRecipesService.findPageRecipe(pageParam, recipesQueryVo);
        return Result.ok(map);
    }

    //添加
    @PostMapping("recipe/saveRecipe")
    public Result<Object> saveRecipe(@RequestBody TRecipes tRecipes) {
        boolean isSuccess = tRecipesService.save(tRecipes);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("recipe/getRecipe/{id}")
    public Result<TRecipes> getRecipe(@PathVariable Long id) {
        TRecipes tRecipes = tRecipesService.getById(id);
        return Result.ok(tRecipes);
    }

    @PostMapping("recipe/updateRecipe")
    public Result<Object> updateById(@RequestBody TRecipes tRecipes) {
        tRecipes.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = tRecipesService.updateById(tRecipes);
        if (isSuccess)
            return Result.ok(null).code(20000).message("修改成功！");
        else
            return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除
    @DeleteMapping("recipe/remove/{id}")
    public Result<Object> removeRecipe(@PathVariable Long id){
        boolean isSuccess = tRecipesService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

}

