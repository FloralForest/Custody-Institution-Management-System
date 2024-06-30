package com.tuoguan.tg.vod.entity.entityVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//相比于TStudents这里做了扩展，用作查询
@Data
public class StudentQueryVo {
	
	@ApiModelProperty(value = "学生姓名")
	private String studentName;

	@ApiModelProperty(value = "年龄范围开始")
	private Integer ageBegin;

	@ApiModelProperty(value = "年龄范围结束")
	private Integer ageEnd;


	@ApiModelProperty(value = "出生日期")
	private String birthdayBegin;

	@ApiModelProperty(value = "出生结束日期")
	private String birthdayEnd;

	@ApiModelProperty(value = "登记日期")
	private String enrollmentDateBegin;

	@ApiModelProperty(value = "登记结束日期")
	private String enrollmentDateEnd;


}

