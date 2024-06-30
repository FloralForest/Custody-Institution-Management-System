package com.tuoguan.tg.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.mapper.TUsersMapper;
import com.tuoguan.tg.vod.service.TUsersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录 前端控制器
 * </p>
 *
 * @author blossom
 * @since 2023-03-23
 */
@CrossOrigin//解决跨域
@RestController //返回json数据
@RequestMapping(value = "/admin/vod/user/")
public class UserLoginController {

    @Resource
    private TUsersService tUsersService;
    @Resource
    private TNoticesController tNoticesController;


    Map<String, Object> userMap = new HashMap<>();

    @PostMapping("userLogin")
    public Result<Map<String, Object>> userLogin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        QueryWrapper<TUsers> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);//判等
        TUsers user = tUsersService.getOne(wrapper);

        if (user == null) { //判断用户名是否存在然后回显给前端
            userMap.put("username", username);
            return Result.loginUserNameFail(userMap);
        } else if (!user.getPassword().equals(password)) { //判断密码是否正确然后回显给前端
            return Result.loginPasswordFail(userMap);
        } else {
            //查询登录角色拥有的公告 和 未读公告
            final int unread = tNoticesController.getUnread(user.getId().longValue(), user.getRole().longValue());

            userMap.put("loginUserId", user.getId());
            userMap.put("loginTeacherId", user.getTeacherId());
            userMap.put("loginUserName", username);
            userMap.put("loginAvatar", user.getAvatar());
            userMap.put("loginRoles", user.getRole());
            userMap.put("loginUnread", unread);
            userMap.put("loginToken", "user-token");
            return Result.ok(userMap);
        }
    }

    @PostMapping("login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        QueryWrapper<TUsers> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);//判等
        TUsers user = tUsersService.getOne(wrapper);

        if (user == null) { //判断用户名是否存在然后回显给前端
            userMap.put("username", username);
            return Result.loginUserNameFail(userMap);
        } else if (!user.getPassword().equals(password)) { //判断密码是否正确然后回显给前端
            return Result.loginPasswordFail(userMap);
        } else if (user.getRole() != 0 && user.getRole() != 1) { //判断用户角色然后回显给前端
            return Result.loginRolesFail(userMap);
        } else {
            userMap.put("userId", user.getId());
            userMap.put("teacherId", user.getTeacherId());
            userMap.put("username", username);
            userMap.put("avatar", user.getAvatar());
            userMap.put("roles", user.getRole());
            userMap.put("token", "admin-token");
            return Result.ok(userMap);
        }
    }

    //获取用户信息
    @GetMapping("info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> map = new HashMap<>();

        map.put("userId", userMap.get("userId"));//用户id
        map.put("teacherId", userMap.get("teacherId"));//获取教师id
        map.put("name", userMap.get("username"));
        map.put("introduction", "我是一名管理员");
        map.put("avatar", userMap.get("avatar"));
        map.put("roles", userMap.get("roles"));
        return Result.ok(map);
    }

    //退出
    @PostMapping("logout")
    public Result<String> logout() {
        return Result.ok("退出成功！");
    }

}

