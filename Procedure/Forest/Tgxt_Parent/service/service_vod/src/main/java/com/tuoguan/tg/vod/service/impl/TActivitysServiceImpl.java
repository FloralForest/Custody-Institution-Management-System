package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.*;
import com.tuoguan.tg.vod.entity.entityDTO.ActivityStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityVo.ActivityTeacherVO;
import com.tuoguan.tg.vod.entity.entityVo.CourseTeacherVO;
import com.tuoguan.tg.vod.mapper.TActivityClassifyMapper;
import com.tuoguan.tg.vod.mapper.TActivitysMapper;
import com.tuoguan.tg.vod.service.TActivityClassifyService;
import com.tuoguan.tg.vod.service.TActivitysService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuoguan.tg.vod.service.TTeachersService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表	 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Service
public class TActivitysServiceImpl extends ServiceImpl<TActivitysMapper, TActivitys> implements TActivitysService {

    @Resource
    private TActivitysMapper tActivitysMapper;
    @Resource
    private TTeachersService teachersService;

    @Resource
    private TActivityClassifyService tActivityClassifyService;

    //查询
    @Override
    public Map<String, Object> findPageActivity(Page<TActivitys> pageParam, ActivityTeacherVO activityTeacherVO) {
        //获取条件值
        String activityName = activityTeacherVO.getActivityName();
        String organizer = activityTeacherVO.getOrganizer();
        Integer activityClassifyId = activityTeacherVO.getActivityClassifyId();
        Integer teacherId = activityTeacherVO.getActivityAgent();
        String startTimeBegin = activityTeacherVO.getStartTimeBegin();
        String startTimeEnd = activityTeacherVO.getStartTimeEnd();

        //封装条件
        //使用QueryWrapper时需要写数据库字段例如：wrapper.eq("parent_id",id);
        LambdaQueryWrapper<TActivitys> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(activityName)) {
            wrapper.like(TActivitys::getActivityName, activityName);
        }
        if (!StringUtils.isEmpty(organizer)) {
            wrapper.eq(TActivitys::getOrganizer, organizer);
        }
        if (!StringUtils.isEmpty(activityClassifyId)) {
            wrapper.eq(TActivitys::getActivityClassifyId, activityClassifyId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq(TActivitys::getActivityAgent, teacherId);
        }
        if (!StringUtils.isEmpty(startTimeBegin)) {//判空
            wrapper.ge(TActivitys::getStartTime, startTimeBegin);//ge大于等于
        }
        if (!StringUtils.isEmpty(startTimeEnd)) {//判空
            wrapper.le(TActivitys::getStartTime, startTimeEnd);//le小于等于
        }
        //调用方法查询(进行条件查询带分页)
        Page<TActivitys> page = baseMapper.selectPage(pageParam, wrapper);
        long total = page.getTotal();//总记录数
        long totalPage = page.getPages();//总页数
        long currentPage = page.getCurrent();//当前页
        long size = page.getSize();//每页记录数
        List<TActivitys> records = page.getRecords();//每页数据集合
        //遍历封装教师和分类名称(根据id查询名称最终显示)
//        records.stream().forEach(flor ->{
//            this.getNameById(flor);
//        });
        records.forEach(this::getNameById);

        //封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", total);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }

    ////获取教师和分类名称
    private void getNameById(TActivitys tActivitys) {
        //查询教师名称
        TTeachers teacher = teachersService.getById(tActivitys.getActivityAgent());
        if (teacher != null) {
            tActivitys.getParam().put("teacherId", teacher.getId());
            tActivitys.getParam().put("teacherName", teacher.getTeacherName());
            tActivitys.getParam().put("level", teacher.getLevel());
            tActivitys.getParam().put("courseMajor", teacher.getTeachersAvatar());
        }

        //查询分类名称
        TActivityClassify tActivityClassify = tActivityClassifyService.getById(tActivitys.getActivityClassifyId());
        if (tActivityClassify != null) {
            tActivitys.getParam().put("classifyName", tActivityClassify.getClassifyName());
        }
    }

    //查询选择该活动的所有学生
    @Override
    public List<ActivityStudentDTO> selectActivityStudent(long id) {
        return tActivitysMapper.selectActivityStudent(id);
    }
}
