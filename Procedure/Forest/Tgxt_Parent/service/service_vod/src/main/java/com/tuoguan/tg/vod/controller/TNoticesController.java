package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.*;
import com.tuoguan.tg.vod.entity.entityVo.NoticeUserVO;
import com.tuoguan.tg.vod.service.TNoticeUserReadService;
import com.tuoguan.tg.vod.service.TNoticesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@RestController
@RequestMapping(value = "/admin/vod/")
@CrossOrigin //解决跨域
public class TNoticesController {
    @Resource
    private TNoticesService tNoticesService;

    @Resource
    private TNoticeUserReadService tNoticeUserReadService;

    //查询所有
    @GetMapping("notice/findAll")
    public Result<List<TNotices>> noticeFindAll() {
        return Result.ok(tNoticesService.list()).code(20000).message("查询数据成功！");
    }

    //当前页，每页记录数以及获取前端传来的查询条件查询对象
    @GetMapping("notice/findQueryPage/{page}/{limit}")
    public Result
            <Map<String, Object>> noticeList(@PathVariable Long page,
                                             @PathVariable Long limit,
                                             NoticeUserVO noticeUserVO) {
        Page<TNotices> pageParam = new Page<>(page, limit);
        Map<String, Object> map = tNoticesService.findPageNotice(pageParam, noticeUserVO);
        return Result.ok(map);
    }

    //添加
    @PostMapping("notice/saveNotice")
    public Result<Object> saveNotice(@RequestBody TNotices tNotices) {
        if (tNotices.getTitle() == null || tNotices.getTitle().trim().isEmpty() ||
                tNotices.getContent() == null || tNotices.getContent().trim().isEmpty()) {
            return Result.fail(null).code(20001).message("标题或内容不能为空");
        }
        boolean isSuccess = tNoticesService.save(tNotices);
        if (isSuccess) return Result.ok(null).code(20000).message("添加成功！");
        else return Result.fail(null).code(20001).message("添加失败！");
    }

    //id查询设置公告阅读
    @GetMapping("notice/getNoticeUserRead/{id}/{loginUserId}")
    public Result<Map<String, Object>> getNoticeUserRead(@PathVariable Long id,
                                                         @PathVariable Long loginUserId) {
        Map<String, Object> map = tNoticesService.selectNoticeUser(id);
        //查询是否已有数据
        final int read = tNoticeUserReadService.getRead(id, loginUserId);
        if (read == 0) {
            final TNoticeUserRead tNoticeUserRead = new TNoticeUserRead();
            tNoticeUserRead.setNoticeId(Math.toIntExact(id));
            tNoticeUserRead.setUserId(Math.toIntExact(loginUserId));
            tNoticeUserReadService.save(tNoticeUserRead);
        }

        return Result.ok(map);
    }

    //查询未阅读数
    @GetMapping("notice/getUnread/{loginUserId}/{loginRoles}")
    public int getUnread(@PathVariable Long loginUserId, @PathVariable Long loginRoles) {
        //查询当前登录角色拥有的公告总数
        final int tNoticesCount = tNoticesService.countByGroup(loginRoles);
        // 获取该用户已阅读的通知数量
        final int readNoticesCount = tNoticeUserReadService.countByUserId(loginUserId);
        return tNoticesCount - readNoticesCount;
    }

    //id查询
    @GetMapping("notice/getNoticeUser/{id}")
    public Result<Map<String, Object>> getNoticeUser(@PathVariable Long id) {
        Map<String, Object> map = tNoticesService.selectNoticeUser(id);
        return Result.ok(map);
    }

    //修改
    @GetMapping("notice/getNotice/{id}")
    public Result<TNotices> getNotice(@PathVariable Long id) {
        TNotices tNotices = tNoticesService.getById(id);
        return Result.ok(tNotices);
    }

    @PostMapping("notice/updateNotice")
    public Result<Object> updateById(@RequestBody TNotices tNotices) {
        tNotices.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = tNoticesService.updateById(tNotices);
        if (isSuccess)
            return Result.ok(null).code(20000).message("修改成功！");
        else
            return Result.fail(null).code(20001).message("修改失败！");
    }

    //逻辑删除
    @DeleteMapping("notice/remove/{id}")
    public Result<Object> removeNotice(@PathVariable Long id) {
        boolean isSuccess = tNoticesService.removeById(id);
        if (isSuccess)
            return Result.ok(null).code(20000).message("删除成功！");
        else
            return Result.fail(null).code(20001).message("删除失败！");
    }

    //批量删除
    @DeleteMapping("notice/removeBatch")
    public Result<Object> removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = tNoticesService.removeByIds(idList);
        if (isSuccess)
            return Result.ok(null).code(20000).message("批量删除成功！");
        else
            return Result.fail(null).code(20001).message("批量删除失败！");
    }

}

