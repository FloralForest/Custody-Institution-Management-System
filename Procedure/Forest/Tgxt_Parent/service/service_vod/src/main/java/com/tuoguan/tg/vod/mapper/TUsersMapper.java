package com.tuoguan.tg.vod.mapper;

import com.tuoguan.tg.vod.entity.TUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表	 Mapper 接口
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-08
 */
@Mapper
public interface TUsersMapper extends BaseMapper<TUsers> {

    //查询所有用户以及关联的信息
    List<TUsers> selectUsersTeachers();

    //根据id查询用户以及关联的信息
    TUsers getUsersTeachersId(long id);
}
