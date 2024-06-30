package com.tuoguan.tg.vod.entity.entityVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//相比于TTeachers这里做了入职时间的扩展，用作查询
@Data
public class TeacherQueryVo {
	
	@ApiModelProperty(value = "教师姓名")
	private String teacherName;

	@ApiModelProperty(value = "头衔 1高级教师 0普通教师")
	private Integer level;

	@ApiModelProperty(value = "入职时间")
	private String hireDateBegin;

	@ApiModelProperty(value = "入职结束时间")
	private String hireDateEnd;


}

