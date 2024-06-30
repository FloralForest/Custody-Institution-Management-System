package com.tuoguan.tg.vod.service;

import com.tgxt.utils.AuthContextHolder;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.entityDTO.TeacherEvaluateDTO;

import java.util.List;

/**
 * <p>
 * 教师表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-07
 */
public interface TTeachersService extends IService<TTeachers> {

    //批量删除用户的教师id
    AuthContextHolder getUserIdsByIds(List<Long> idList);

    //查询指定id的教师的评论
    List<TeacherEvaluateDTO> selectEvaluate(long id);
}
