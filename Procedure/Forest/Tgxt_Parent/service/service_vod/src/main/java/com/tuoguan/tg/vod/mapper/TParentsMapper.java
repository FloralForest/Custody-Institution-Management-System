package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TParents;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuoguan.tg.vod.entity.TStudents;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.entityDTO.ParentStudentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 家长表 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-10
 */
@Mapper
public interface TParentsMapper extends BaseMapper<TParents> {
    //查询ta的子女
    List<ParentStudentDTO> selectParentStudent(long parentId);
}
