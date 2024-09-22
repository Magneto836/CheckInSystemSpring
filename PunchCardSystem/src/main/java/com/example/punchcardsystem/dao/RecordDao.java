package com.example.punchcardsystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.punchcardsystem.pojo.RecordPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface RecordDao extends BaseMapper<RecordPojo> {
    @Select("SELECT * FROM time_records WHERE userId = #{userId} AND clockInDate = #{date}")
    RecordPojo findByUserIdAndDate(int userId, Date date);  // 自定义方法

    @Select("SELECT * FROM time_records WHERE YEAR(clockInDate) = #{year} AND MONTH(clockInDate) = #{month}")
    List<RecordPojo> findByYearAndMonth(@Param("year") int year, @Param("month") int month);



}






