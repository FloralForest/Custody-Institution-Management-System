package com.tuoguan.tg.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tuoguan.tg.vod.entity.TActivityClassify;
import com.tuoguan.tg.vod.entity.TClassify;
import com.tuoguan.tg.vod.entity.entityVo.ClassifyVO;
import com.tuoguan.tg.vod.mapper.TActivityClassifyMapper;
import com.tuoguan.tg.vod.service.TActivityClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuoguan.tg.vod.utils.ClassifyListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程分类表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Service
public class TActivityClassifyServiceImpl extends ServiceImpl<TActivityClassifyMapper, TActivityClassify> implements TActivityClassifyService {
    //导出
    @Override
    public void exportData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName;
        try {
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode("活动分类", "UTF-8");
            //设置头信息
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //查询活动分类所有数据
            List<TActivityClassify> tClassifyList = baseMapper.selectList(null);
            //把List<TActivityClassify>变成List<ClassifyVO>
            List<ClassifyVO> classifyVOList = new ArrayList<>(tClassifyList.size());
            for (TActivityClassify tActivityClassify : tClassifyList) {
                ClassifyVO classifyVO = new ClassifyVO();
                classifyVO.setActivityName(tActivityClassify.getClassifyName());
                classifyVOList.add(classifyVO);
            }

            EasyExcel.write(response.getOutputStream(), ClassifyVO.class).sheet("活动分类").doWrite(classifyVOList);
        } catch (UnsupportedEncodingException e) {
            // log the exception and re-throw it
            throw new RuntimeException("Encoding error: " + e.getMessage(), e);
        } catch (IOException e) {
            // log the exception and re-throw it
            throw new RuntimeException("IO error: " + e.getMessage(), e);
        } catch (Exception e) {
            // log the exception and re-throw it
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    //导入
    @Resource
    private ClassifyListener classifyListener;

    @Override
    public void importDictData(MultipartFile file) {
        // 这里需要指定读用哪个class去读，然后读取第一个sheet   文件流会自动关闭
        try {
            EasyExcel.read(file.getInputStream(), ClassifyVO.class, classifyListener).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
