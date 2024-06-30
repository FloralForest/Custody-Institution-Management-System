package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TActivityClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 课程分类表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
public interface TActivityClassifyService extends IService<TActivityClassify> {

    //导出
    void exportData(HttpServletResponse response);

    //导入
    void importDictData(MultipartFile file);
}
