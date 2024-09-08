package com.example.punchcardsystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.punchcardsystem.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsDao extends BaseMapper<StatisticsPojo> {
}
