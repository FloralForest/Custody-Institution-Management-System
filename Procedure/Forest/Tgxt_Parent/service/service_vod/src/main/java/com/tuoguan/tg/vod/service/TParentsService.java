package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TParents;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tuoguan.tg.vod.entity.TStudents;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;

import java.util.List;

/**
 * <p>
 * 家长表 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-10
 */
public interface TParentsService extends IService<TParents> {
    //查询ta的子女
    List<ParentStudentDTO> selectParentStudent(Long parentId);
}
