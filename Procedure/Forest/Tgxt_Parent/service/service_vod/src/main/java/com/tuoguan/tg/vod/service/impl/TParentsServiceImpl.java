package com.tuoguan.tg.vod.service.impl;

import com.tuoguan.tg.vod.entity.TParents;
import com.tuoguan.tg.vod.entity.TStudents;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;
import com.tuoguan.tg.vod.mapper.TParentsMapper;
import com.tuoguan.tg.vod.mapper.TStudentsMapper;
import com.tuoguan.tg.vod.service.TParentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 家长表 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-10
 */
@Service
public class TParentsServiceImpl extends ServiceImpl<TParentsMapper, TParents> implements TParentsService {

    @Resource
    private TParentsMapper tParentsMapper;

    @Override
    public List<ParentStudentDTO> selectParentStudent(Long parentId) {
        return tParentsMapper.selectParentStudent(parentId);
    }
}
