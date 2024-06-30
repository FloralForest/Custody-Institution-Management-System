package com.tuoguan.tg.vod.entity.entityVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//这里做了扩展，用作查询
@Data
public class RecipesQueryVo {

	/**
	 * 食谱名
	 */
	private String recipesName;

	/**
	 * 食谱分类
	 */
	private String recipesClassify;

	/**
	 * 日期范围开始
	 */
	private String recipeDateBegin;

	/**
	 * 日期范围结束
	 */
	private String recipeDateEnd;

}

