package com.example.punchcardsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.punchcardsystem.dao.UserDao;
import com.example.punchcardsystem.dto.UserLoginResponse;
import com.example.punchcardsystem.pojo.UserPojo;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl {

    @Autowired
    UserDao userDao;


    /*
        要实现的功能:
        1. 注册添加数据 √
        2. 登录账号匹配 √
        3. （修改密码）


     */




    public int  addUser(String username, String password,
                        String role) {
        UserPojo existingUser = userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", username));

        if (existingUser != null) {
            throw new RuntimeException("用户名已存在，请选择其他用户名");
        }

        UserPojo newUser = new UserPojo();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        userDao.insert(newUser);
        return newUser.getId();

    }

    // 匹配账号密码
    public UserLoginResponse verifyUserCredentials(String username, String password) {
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);  // 仅根据用户名查询

        UserPojo user = userDao.selectOne(queryWrapper);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }

    return new UserLoginResponse(user.getId(), user.getRole());
    }

}
