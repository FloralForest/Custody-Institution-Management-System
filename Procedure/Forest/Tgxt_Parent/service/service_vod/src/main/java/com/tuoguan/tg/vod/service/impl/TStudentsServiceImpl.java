package com.tuoguan.tg.vod.service.impl;

import com.tuoguan.tg.vod.entity.*;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentCourseIntelDTO;
import com.tuoguan.tg.vod.mapper.TParentsStudentsMapper;
import com.tuoguan.tg.vod.mapper.TStudentsMapper;
import com.tuoguan.tg.vod.service.TParentsService;
import com.tuoguan.tg.vod.service.TParentsStudentsService;
import com.tuoguan.tg.vod.service.TStudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-13
 */
@Service
public class TStudentsServiceImpl extends ServiceImpl<TStudentsMapper, TStudents> implements TStudentsService {

    @Resource
    private TStudentsMapper tStudentsMapper;

    @Resource
    private TParentsService tParentsService;

    @Resource
    private TParentsStudentsMapper tParentsStudentsMapper;

    //查询指定id学生选择的所有课程
    @Override
    public List<StudentCourseIntelDTO> selectStudentCourse(long id) {
        return tStudentsMapper.selectStudentCourse(id);
    }

    //查询指定id学生选择的所有活动
    @Override
    public List<StudentActivityIntelDTO> selectStudentActivity(long id) {
        return tStudentsMapper.selectStudentActivity(id);
    }

    //查询学生以及学生家长
    @Override
    public Map<String, Object> findStudentParent(Long id) {
        final TStudents student = baseMapper.selectById(id);
        TParentsStudents tParentsStudent = tParentsStudentsMapper.findByStudentId(id);
        if (tParentsStudent != null){
            //获取家长
            final TParents tParent = tParentsService.getById(tParentsStudent.getParentId());
            student.getParam().put("parentName", tParent.getParentName());
        }
        //封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("student", student);
        return map;
    }
}
