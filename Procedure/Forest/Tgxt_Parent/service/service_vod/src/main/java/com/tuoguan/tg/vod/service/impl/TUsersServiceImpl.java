package com.tuoguan.tg.vod.service.impl;

import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.mapper.TUsersMapper;
import com.tuoguan.tg.vod.service.TUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表	 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-03-08
 */
@Service
public class TUsersServiceImpl extends ServiceImpl<TUsersMapper, TUsers> implements TUsersService {

    @Resource
    private TUsersMapper tUsersMapper;

    @Override
    public List<TUsers> selectUsersTeachers() {
        return tUsersMapper.selectUsersTeachers();
    }

    @Override
    public TUsers getUsersTeachersId(long id) {
        return tUsersMapper.getUsersTeachersId(id);
    }
}
