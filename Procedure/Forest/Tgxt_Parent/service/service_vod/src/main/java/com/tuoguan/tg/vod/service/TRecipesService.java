package com.tuoguan.tg.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.TRecipes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.entityVo.RecipesQueryVo;

import java.util.Map;

/**
 * <p>
 * 每日食谱表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-07
 */
public interface TRecipesService extends IService<TRecipes> {
    //查询以及分页
    Map<String, Object> findPageRecipe(Page<TRecipes> pageParam, RecipesQueryVo recipesQueryVo);
}
