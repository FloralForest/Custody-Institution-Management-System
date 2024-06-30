package com.tuoguan.tg.vod.service.impl;

import com.tuoguan.tg.vod.entity.TTasks;
import com.tuoguan.tg.vod.entity.entityDTO.StudentTaskDTO;
import com.tuoguan.tg.vod.mapper.TTasksMapper;
import com.tuoguan.tg.vod.service.TTasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 作业辅导 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-08
 */
@Service
public class TTasksServiceImpl extends ServiceImpl<TTasksMapper, TTasks> implements TTasksService {
    @Resource
    private TTasksMapper tTasksMapper;

    //查询指定id的学生作业辅导
    @Override
    public List<StudentTaskDTO> studentTask(long id) {
        return tTasksMapper.studentTask(id);
    }
}
