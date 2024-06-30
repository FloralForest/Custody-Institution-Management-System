package com.tuoguan.tg.vod.service;

import com.tuoguan.tg.vod.entity.TUsers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表	 服务类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-08
 */
public interface TUsersService extends IService<TUsers> {

    //查询所有用户以及关联的信息
    List<TUsers> selectUsersTeachers();

    //根据id查询用户以及关联的信息
    TUsers getUsersTeachersId(long id);

}
