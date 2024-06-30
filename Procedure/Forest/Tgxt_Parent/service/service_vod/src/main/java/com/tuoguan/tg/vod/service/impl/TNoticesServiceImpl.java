package com.tuoguan.tg.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuoguan.tg.vod.entity.*;
import com.tuoguan.tg.vod.entity.entityVo.NoticeUserVO;
import com.tuoguan.tg.vod.mapper.TNoticesMapper;
import com.tuoguan.tg.vod.service.TNoticesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuoguan.tg.vod.service.TTeachersService;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-21
 */
@Service
public class TNoticesServiceImpl extends ServiceImpl<TNoticesMapper, TNotices> implements TNoticesService {

    @Resource
    private TUsersService tUsersService;

    @Resource
    private TTeachersService teachersService;

    @Resource
    private TNoticesMapper tNoticesMapper;

    //查询
    @Override
    public Map<String, Object> findPageNotice(Page<TNotices> pageParam, NoticeUserVO noticeUserVO) {
        //获取条件值
        String title = noticeUserVO.getTitle();
        Integer role = noticeUserVO.getRole();//当前登录角色
        Integer priority = noticeUserVO.getPriority();
        String publishDateBegin = noticeUserVO.getPublishDateBegin();
        String publishDateEnd = noticeUserVO.getPublishDateEnd();

        //封装条件
        LambdaQueryWrapper<TNotices> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like(TNotices::getTitle, title);
        }
        if (!StringUtils.isEmpty(priority)) {
            wrapper.eq(TNotices::getPriority, priority);
        }
        if (!StringUtils.isEmpty(publishDateBegin)) {//判空
            wrapper.ge(TNotices::getPublishDate, publishDateBegin);//ge大于等于
        }
        if (!StringUtils.isEmpty(publishDateEnd)) {//判空
            wrapper.le(TNotices::getPublishDate, publishDateEnd);//le小于等于
        }
        // 根据role查询Group字段 0为管理员
        if (noticeUserVO.getRole() != null && noticeUserVO.getRole() !=0) {
            //查询与当前登录角相同的公告查看群体(1教师,2家长,3全部),与无论何种角色都需要查询出的"全部"
            wrapper.and(wq -> wq.eq(TNotices::getGroup, role).or().eq(TNotices::getGroup, 3));
        }
        wrapper.orderByAsc(TNotices::getPriority); // 按照priority(优先级)字段升序排序,该方法分页时不影响最终结果{先排序再插入}

        //调用方法查询(进行条件查询带分页)
        Page<TNotices> page = baseMapper.selectPage(pageParam, wrapper);
        long total = page.getTotal();//总记录数
        long totalPage = page.getPages();//总页数
        long currentPage = page.getCurrent();//当前页
        long size = page.getSize();//每页记录数
        List<TNotices> records = page.getRecords();//每页数据集合
//        // 对records按照priority(优先级)字段进行升序排序，这个方法分页时影响了最终结果{先查询再排序}
//        records.sort(Comparator.comparing(TNotices::getPriority));
        //根据id查询名称最终显示
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

    //获取数据
    private void getNameById(TNotices tNotices) {
        //查询名称
        TUsers tUsers = tUsersService.getById(tNotices.getPublisher());
        if (tUsers != null) {
            tNotices.getParam().put("usersName", tUsers.getUsername());
            tNotices.getParam().put("role", tUsers.getRole());
            tNotices.getParam().put("teacherId", tUsers.getTeacherId());

            if (tUsers.getTeacherId() != null && tUsers.getTeacherId() != 0) {
                //查询该用户的教师名称(如果有的话)
                final TTeachers tTeachers = teachersService.getById(tUsers.getTeacherId());
                tNotices.getParam().put("teacherName", tTeachers.getTeacherName());
            }
        }
    }

    //id查询公告以及用户
    @Override
    public Map<String, Object> selectNoticeUser(Long id) {
        TNotices tNotices = baseMapper.selectById(id);
        String userName = getUserById(tNotices.getPublisher());
        //封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("tNotices", tNotices);
        map.put("userName", userName);
        return map;
    }

    //获取数据
    private String getUserById(long id) {
        //查询名称
        TUsers tUsers = tUsersService.getById(id);
        return tUsers.getUsername();
    }

    //查询当前登录角色拥有的公告总数
    @Override
    public int countByGroup(Long group) {
        return tNoticesMapper.countByGroup(group);
    }
}
