package com.example.punchcardsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.punchcardsystem.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserPojo> {



}
