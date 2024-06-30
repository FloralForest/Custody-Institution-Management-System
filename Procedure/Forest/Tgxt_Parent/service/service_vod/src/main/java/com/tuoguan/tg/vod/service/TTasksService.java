package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TTasks;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.entityDTO.StudentTaskDTO;

import java.util.List;

/**
 * <p>
 * 作业辅导 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-05-08
 */
public interface TTasksService extends IService<TTasks> {

    //查询指定id的学生作业辅导
    List<StudentTaskDTO> studentTask(long id);
}
