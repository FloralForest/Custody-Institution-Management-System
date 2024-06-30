package com.tuoguan.tg.vod.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tuoguan.tg.vod.entity.TActivityClassify;
import com.tuoguan.tg.vod.entity.TClassify;
import com.tuoguan.tg.vod.entity.entityVo.ClassifyVO;
import com.tuoguan.tg.vod.mapper.TActivityClassifyMapper;
import com.tuoguan.tg.vod.mapper.TActivitysMapper;
import com.tuoguan.tg.vod.mapper.TClassifyMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//创建读取监听器（excel）   导入
@Component//交给spring管理
public class ClassifyListener extends AnalysisEventListener<ClassifyVO> {

    //注入mapper
    @Resource
    private TClassifyMapper tClassifyMapper;
    @Resource
    private TActivityClassifyMapper tActivityClassifyMapper;

    //一行一行读取excel内容，把内容封装到对象（但是机器认为第一行是表头会从第二行读取）
    @Override
    public void invoke(ClassifyVO classifyVO, AnalysisContext analysisContext) {
        //调用方法添加数据库
        if (classifyVO.getClassifyName() != null) {
            final List<String> classifyNameList = tClassifyMapper.selectByName();
            // 检查当前的classifyVO的classifyName是否在已存在的分类名称列表中,存在跳过读excel的下一条
            if (!classifyNameList.contains(classifyVO.getClassifyName())) {
                TClassify classify = new TClassify();
                //classifyVO变成classify
                //工具类把一个对象的值复制到另一个对象中去(名称一样才复制不一样就不会复制)
                BeanUtils.copyProperties(classifyVO, classify);
                tClassifyMapper.insert(classify);
            }
        } else {
            final List<String> list = tActivityClassifyMapper.selectByName();
            if (!list.contains(classifyVO.getActivityName())) {

                TActivityClassify tActivityClassify = new TActivityClassify();
                tActivityClassify.setClassifyName(classifyVO.getActivityName());
                tActivityClassifyMapper.insert(tActivityClassify);
            }
        }

    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
