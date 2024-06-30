package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TCourseEvaluate;
import com.tuoguan.tg.vod.entity.THealth;
import com.tuoguan.tg.vod.entity.entityDTO.CourseEvaluateDTO;
import com.tuoguan.tg.vod.entity.entityDTO.StudentActivityIntelDTO;
import com.tuoguan.tg.vod.entity.entityVo.HealthQueryVo;
import com.tuoguan.tg.vod.service.TCourseEvaluateService;
import com.tuoguan.tg.vod.service.TCoursesService;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程评价 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-26
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TCourseEvaluateController {

    @Resource
    private TCourseEvaluateService tCourseEvaluateService;

    @Resource
    private TCoursesService tCoursesService;

    @Resource
    private TUsersService tUsersService;

    //    //分页和条件查询
//    @PostMapping("evaluate/findQueryPage/{page}/{limit}")
//    //当前页和每页显示个数 @RequestBody接收json数据 @ResponseBody返回json数据 @PathVariable用于获取URL中路径的参数值
//    public Result<IPage<TCourseEvaluate>> findPage(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) TCourseEvaluate tCourseEvaluate) {
//        //创建page（分页）对象
//        Page<TCourseEvaluate> pageParam = new Page<>(page, limit);
//        if (tCourseEvaluate == null) {//查询全部 没有条件
//            IPage<TCourseEvaluate> pageModel = tCourseEvaluateService.page(pageParam, null);
//            return Result.ok(pageModel);
//        }
//        else {
//            //获取条件值
//            Integer courseId = tCourseEvaluate.getCourseId();
//            //非空判断，条件封装
//            LambdaQueryWrapper<TCourseEvaluate> wrapper = new LambdaQueryWrapper<>();
//            if (!StringUtils.isEmpty(courseId)) {//判空
//                wrapper.eq(TCourseEvaluate::getCourseId, courseId);
//            }
//            //调用方法得到分页查询结果
//            IPage<TCourseEvaluate> pageModel = tCourseEvaluateService.page(pageParam, wrapper);
//            return Result.ok(pageModel);
//        }
//    }
    //查询选择指定课程的全部评论
    @GetMapping("activityChartEvaluate/{id}")
    public Result<Map<String, Object>> activityChartEvaluateAll(@PathVariable long id) {

        final List<CourseEvaluateDTO> courseEvaluateDTOList = tCoursesService.selectEvaluate(id);
        final Map<String, Object> map = new HashMap<>();
        map.put("courseEvaluateDTOList", courseEvaluateDTOList);
        return Result.ok(map).code(20000).message("查询数据成功！");
    }

    //添加
    @PostMapping("evaluate/saveEvaluate")
    public Result<Object> saveEvaluate(@RequestBody TCourseEvaluate tCourseEvaluate) {
        if (tCourseEvaluate.getFraction() == 0 ||
                tCourseEvaluate.getContent() == null || tCourseEvaluate.getContent().trim().isEmpty()) {
            return Result.fail(null).code(20001).message("评分或内容不能为空");
        }
        boolean isSuccess = tCourseEvaluateService.save(tCourseEvaluate);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //修改
    @GetMapping("evaluate/getEvaluate/{id}")
    public Result<TCourseEvaluate> getEvaluate(@PathVariable Long id) {
        TCourseEvaluate tCourseEvaluate = tCourseEvaluateService.getById(id);
        return Result.ok(tCourseEvaluate).code(20000);
    }

    @PostMapping("evaluate/updateEvaluate")
    public Result<Object> updateById(@RequestBody TCourseEvaluate tCourseEvaluate) {
        boolean isSuccess = tCourseEvaluateService.updateById(tCourseEvaluate);
        if (isSuccess) return Result.ok(null).code(20000).message("修改成功！");
        else return Result.fail(null).code(20001).message("修改失败！");
    }

    //删除
    @Transactional//spring统一管理事务
    @DeleteMapping("evaluate/remove/{id}")
    public Result<Object> removeEvaluate(@PathVariable Long id) {
        //删除
        boolean isSuccess = tCourseEvaluateService.removeById(id);
        if (isSuccess) return Result.ok(null).code(20000).message("删除成功！");
        else return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("evaluate/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = tCourseEvaluateService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }

}

