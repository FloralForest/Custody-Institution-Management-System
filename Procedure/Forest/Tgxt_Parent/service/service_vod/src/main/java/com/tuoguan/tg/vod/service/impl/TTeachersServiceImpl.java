package com.tuoguan.tg.vod.service.impl;

import com.tgxt.utils.AuthContextHolder;
import com.tuoguan.tg.vod.entity.TTeachers;
import com.tuoguan.tg.vod.entity.entityDTO.TeacherEvaluateDTO;
import com.tuoguan.tg.vod.mapper.TTeachersMapper;
import com.tuoguan.tg.vod.service.TTeachersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-07
 */
@Service
public class TTeachersServiceImpl extends ServiceImpl<TTeachersMapper, TTeachers> implements TTeachersService {
    @Resource
    private TTeachersMapper tTeachersMapper;

    @Override
    public AuthContextHolder getUserIdsByIds(List<Long> idList) {
        return null;
    }

    @Override
    public List<TeacherEvaluateDTO> selectEvaluate(long id) {
        return tTeachersMapper.selectEvaluate(id);
    }
}
