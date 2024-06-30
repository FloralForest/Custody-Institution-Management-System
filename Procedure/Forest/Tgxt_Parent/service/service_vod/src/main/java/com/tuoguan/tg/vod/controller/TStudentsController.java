package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TParents;
import com.tuoguan.tg.vod.entity.TParentsStudents;
import com.tuoguan.tg.vod.entity.TStudents;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.entityDTO.CourseStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentCourseIntelDTO;
import com.tuoguan.tg.vod.entity.entityVo.StudentQueryVo;
import com.tuoguan.tg.vod.service.TParentsStudentsService;
import com.tuoguan.tg.vod.service.TStudentsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-13
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TStudentsController {
    @Resource
    private TStudentsService studentsService;

    //家长学生关联
    @Resource
    private TParentsStudentsService parentsStudentsService;

    //查询所有学生
    @GetMapping("student/findAll")
    public Result<List<TStudents>> findAll() {
        return Result.ok(studentsService.list()).code(20000).message("查询学生数据成功！");
    }

    //查询选择指定学生的全部课程
    @GetMapping("chartStudent/{id}")
    public Result<Map<String, Object>> chartStudentAll(@PathVariable long id) {

        final List<StudentCourseIntelDTO> studentCourseIntelDTOList = studentsService.selectStudentCourse(id);
        final Map<String, Object> map = new HashMap<>();
        map.put("studentCourseIntelDTOList",studentCourseIntelDTOList);
        return Result.ok(map).code(20000).message("查询课程数据成功！");
    }
    //查询选择指定学生的全部活动
    @GetMapping("activityChartStudent/{id}")
    public Result<Map<String, Object>> activityChartStudentAll(@PathVariable long id) {

        final List<StudentActivityIntelDTO> studentActivityIntelDTOList = studentsService.selectStudentActivity(id);
        final Map<String, Object> map = new HashMap<>();
        map.put("studentActivityIntelDTOList",studentActivityIntelDTOList);
        return Result.ok(map).code(20000).message("查询课程数据成功！");
    }

    //分页和条件查询学生
    @PostMapping("student/findQueryPage/{current}/{limit}")
    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
    public Result<IPage<TStudents>> findPage(@PathVariable long current,
                                             @PathVariable long limit,
                                             @RequestBody(required = false) StudentQueryVo studentQueryVo) {
        //创建page（分页）对象
        Page<TStudents> pageParam = new Page<>(current, limit);
        if (studentQueryVo == null) {//查询全部 没有条件
            IPage<TStudents> pageModel = studentsService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            //获取条件值
            String studentName = studentQueryVo.getStudentName();
            Integer ageBegin = studentQueryVo.getAgeBegin();
            Integer ageEnd = studentQueryVo.getAgeEnd();
            String birthdayBegin = studentQueryVo.getBirthdayBegin();
            String birthdayEnd = studentQueryVo.getBirthdayEnd();
            String enrollmentDateBegin = studentQueryVo.getEnrollmentDateBegin();
            String enrollmentDateEnd = studentQueryVo.getEnrollmentDateEnd();
            //非空判断，条件封装
            QueryWrapper<TStudents> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(studentName)) {//判空
                wrapper.like("student_name", studentName);
            }
            if (!StringUtils.isEmpty(ageBegin)) {//判空
                wrapper.ge("age", ageBegin);//ge大于等于
            }
            if (!StringUtils.isEmpty(ageEnd)) {//判空
                wrapper.le("age", ageEnd);//le小于等于
            }
            if (!StringUtils.isEmpty(birthdayBegin)) {//判空
                wrapper.ge("birthday", birthdayBegin);//ge大于等于
            }
            if (!StringUtils.isEmpty(birthdayEnd)) {//判空
                wrapper.le("birthday", birthdayEnd);//le小于等于
            }
            if (!StringUtils.isEmpty(enrollmentDateBegin)) {//判空
                wrapper.ge("enrollment_date", enrollmentDateBegin);//ge大于等于
            }
            if (!StringUtils.isEmpty(enrollmentDateEnd)) {//判空
                wrapper.le("enrollment_date", enrollmentDateEnd);//le小于等于
            }
            //调用方法得到分页查询结果
            IPage<TStudents> pageModel = studentsService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    //添加
    @PostMapping("student/saveStudent")
    public Result<Object> saveStudent(@RequestBody TStudents students) {
        boolean isSuccess = studentsService.save(students);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

//    //修改
//    @GetMapping("student/getStudent/{id}")
//    public Result<TStudents> getStudent(@PathVariable Long id) {
//        TStudents students = studentsService.getById(id);
//        return Result.ok(students);
//    }
    //修改
    @GetMapping("student/getStudent/{id}")
    public Result<Map<String, Object>> getStudent(@PathVariable Long id) {
        Map<String, Object> map = studentsService.findStudentParent(id);
        return Result.ok(map);
    }

    @PostMapping("student/updateStudent")
    public Result<Object> updateById(@RequestBody TStudents students) {
        students.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = studentsService.updateById(students);
        if (isSuccess)
            return Result.ok(null).code(20000).message("修改成功！");
        else
            return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除(非逻辑删除)
    @DeleteMapping("student/remove/{id}")
    public Result<Object> removeStudent(@PathVariable Long id) {
        //删除关联表中对应的子女
        final QueryWrapper<TParentsStudents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", id);
        parentsStudentsService.remove(queryWrapper);

        //最后删除子女
        boolean isSuccess = studentsService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除(非逻辑删除)
    @DeleteMapping("student/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList) {
        //删除关联
        for (Long id : idList) {
            QueryWrapper<TParentsStudents> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id",id);
            parentsStudentsService.remove(queryWrapper);
        }
        //最后删除子女
        boolean isSuccess = studentsService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }

    //家长绑定学生
    @PostMapping("student/saveStudent/{id}")
    public Result<Object> bindStudent(@PathVariable long id,
                                     @RequestBody TStudents students) {
        boolean isStudent = studentsService.save(students);
        if (id != 0) {
            //插入学生表后拿到学生id
            Long studentsId = students.getId();
            final TParentsStudents tParentsStudents = new TParentsStudents();
            //向关联表表插入获取的家长id
            tParentsStudents.setParentId(Math.toIntExact(id));

            //向关联表表插入获取的学生id
            tParentsStudents.setStudentId(Math.toIntExact(studentsId));
            parentsStudentsService.save(tParentsStudents);
        }
        if (isStudent)
            return Result.ok(null).code(20000).message("绑定成功！");
        else
            return Result.fail(null).code(20001).message("绑定失败！");
    }

}

