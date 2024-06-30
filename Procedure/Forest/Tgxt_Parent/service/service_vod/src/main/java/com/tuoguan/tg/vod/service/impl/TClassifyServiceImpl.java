package com.tuoguan.tg.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tuoguan.tg.vod.entity.TClassify;
import com.tuoguan.tg.vod.entity.entityVo.ClassifyVO;
import com.tuoguan.tg.vod.mapper.TClassifyMapper;
import com.tuoguan.tg.vod.service.TClassifyService;
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
 * 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-16
 */
@Service
public class TClassifyServiceImpl extends ServiceImpl<TClassifyMapper, TClassify> implements TClassifyService {

    //导出
    @Override
    public void exportData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName;
        try {
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode("课程分类", "UTF-8");
            //设置头信息
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //查询课程分类所有数据
            List<TClassify> tClassifyList = baseMapper.selectList(null);
            //把List<TClassify>变成List<ClassifyVO>
            List<ClassifyVO> classifyVOList = new ArrayList<>(tClassifyList.size());
            for (TClassify tClassify : tClassifyList) {
                ClassifyVO classifyVO = new ClassifyVO();
                //工具类把一个对象的值复制到另一个对象中去(名称一样才复制不一样就不会复制)
                BeanUtils.copyProperties(tClassify, classifyVO);
                classifyVOList.add(classifyVO);
            }

            EasyExcel.write(response.getOutputStream(), ClassifyVO.class).sheet("课程分类").doWrite(classifyVOList);
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
