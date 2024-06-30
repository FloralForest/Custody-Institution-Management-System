package com.tuoguan.tg.vod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 健康知识表
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TKnowledge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 健康知识id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 知识标题
     */
    private String knowledgeTitle;

    /**
     * 知识内容
     */
    private String knowledgeContent;

    /**
     * 来源
     */
    private String source;

    /**
     * 附件链接
     */
    private String attachmentLink;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
