package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.TActivitys;
import com.tuoguan.tg.vod.entity.TRecipes;
import com.tuoguan.tg.vod.entity.entityVo.RecipesQueryVo;
import com.tuoguan.tg.vod.mapper.TRecipesMapper;
import com.tuoguan.tg.vod.service.TRecipesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 每日食谱表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-07
 */
@Service
public class TRecipesServiceImpl extends ServiceImpl<TRecipesMapper, TRecipes> implements TRecipesService {

    @Override
    public Map<String, Object> findPageRecipe(Page<TRecipes> pageParam, RecipesQueryVo recipesQueryVo) {

        //获取条件值
        String recipesName = recipesQueryVo.getRecipesName();
        String recipesClassify = recipesQueryVo.getRecipesClassify();
        String recipeDateBegin = recipesQueryVo.getRecipeDateBegin();
        String recipeDateEnd = recipesQueryVo.getRecipeDateEnd();

        //封装条件
        //使用QueryWrapper时需要写数据库字段例如：wrapper.eq("parent_id",id);
        LambdaQueryWrapper<TRecipes> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(recipesName)) {
            wrapper.like(TRecipes::getRecipeName, recipesName);
        }
        if (!StringUtils.isEmpty(recipesClassify)) {
            wrapper.eq(TRecipes::getRecipeClassify, recipesClassify);
        }
        if (!StringUtils.isEmpty(recipeDateBegin)) {//判空
            wrapper.ge(TRecipes::getRecipeDate, recipeDateBegin);//ge大于等于
        }
        if (!StringUtils.isEmpty(recipeDateEnd)) {//判空
            wrapper.le(TRecipes::getRecipeDate, recipeDateEnd);//le小于等于
        }
        //调用方法查询(进行条件查询带分页)
        Page<TRecipes> page = baseMapper.selectPage(pageParam, wrapper);
        long total = page.getTotal();//总记录数
        long totalPage = page.getPages();//总页数
        long currentPage = page.getCurrent();//当前页
        long size = page.getSize();//每页记录数
        List<TRecipes> records = page.getRecords();//每页数据集合

        //封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", total);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }
}
