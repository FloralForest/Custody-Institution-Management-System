package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-16
 */
public interface TClassifyService extends IService<TClassify> {

    //导出
    void exportData(HttpServletResponse response);

    //导入
    void importDictData(MultipartFile file);
}
