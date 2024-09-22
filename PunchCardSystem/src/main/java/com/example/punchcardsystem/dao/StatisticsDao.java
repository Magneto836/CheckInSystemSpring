package com.example.punchcardsystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.punchcardsystem.pojo.StatisticsPojo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StatisticsDao extends BaseMapper<StatisticsPojo> {

    // 插入或更新统计数据
    @Insert("INSERT INTO monthly_work_stats (user_id, total_hours, month, year, ranking,username) " +
            "VALUES (#{user_id}, #{total_hours}, #{month}, #{year}, #{ranking},#{username}) " +
            "ON DUPLICATE KEY UPDATE total_hours = VALUES(total_hours), ranking = VALUES(ranking), year = VALUES(year),username = VALUES(username)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void upsertStatistics(StatisticsPojo statisticsPojo);

    // 查询某用户在某年某月的统计记录是否存在
    @Select("SELECT * FROM monthly_work_stats WHERE user_id = #{userId} AND month = #{month} AND year = #{year}")
    StatisticsPojo findByUserIdMonthAndYear(@Param("userId") String userId, @Param("month") int month, @Param("year") int year);
}
