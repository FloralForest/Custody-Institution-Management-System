package com.tuoguan.tg.vod.entity.entityVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifyVO {
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 活动分类名称
     */
    private String activityName;

}
