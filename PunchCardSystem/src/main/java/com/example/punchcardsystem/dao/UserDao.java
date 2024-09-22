package com.example.punchcardsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.punchcardsystem.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<UserPojo> {

    @Select("SELECT username FROM users WHERE id = #{userId}")
    String findUsernameById(int userId);  // 使用 SQL 查询

}
