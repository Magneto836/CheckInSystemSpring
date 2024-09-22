package com.example.punchcardsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.punchcardsystem.dao.CompanyDao;
import com.example.punchcardsystem.dao.RecordDao;
import com.example.punchcardsystem.dao.StatisticsDao;
import com.example.punchcardsystem.dao.UserDao;
import com.example.punchcardsystem.pojo.CompanyPojo;
import com.example.punchcardsystem.pojo.RecordPojo;
import com.example.punchcardsystem.pojo.StatisticsPojo;
import com.example.punchcardsystem.pojo.UserPojo;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordServiceImpl {

    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private StatisticsDao statisticsDao; // 注入mapper

    @Autowired
    private CompanyDao companyDao;  // 假设有一个 CompanyDao 来获取公司的上下班时间
    /*
    * 功能：
    * 1. 插入打卡数据
    * 2. 更新打卡数据
    * 3.
    *   定义 late 为迟到， early 为早退 , absence 为既迟到又早退
    *
    * */
    //上班打卡


    public void saveMonthlyStats(List<StatisticsPojo> stats) {
        for (StatisticsPojo stat : stats) {
            // 先查询该用户在某年某月的记录是否已存在
            StatisticsPojo existingStat = statisticsDao.findByUserIdMonthAndYear(stat.getUser_id(), stat.getMonth(), stat.getYear());

            if (existingStat != null) {
                // 如果记录已存在，直接返回现有记录的id
                System.out.println("Record already exists, returning existing record ID: " + existingStat.getId());
                System.out.println("Record already exists, returning existing record ID: " + existingStat.getUsername());
                stat.setId(existingStat.getId());
            } else {
                // 如果记录不存在，执行插入操作，并返回新生成的id
                statisticsDao.insert(stat);  // 使用MyBatis-Plus的插入方法
                System.out.println("Inserted new record ID: " + stat.getId());
            }
        }
    }

    public List<StatisticsPojo> calculateAndSaveMonthlyWorkStats( int year , int month) {
        // 计算每个用户的总工作时间
        List<StatisticsPojo> stats = getMonthlyWorkStats(year ,month);

        // 保存统计数据到static_record表
        saveMonthlyStats(stats);

        return stats;
    }

 // 修改逻辑，增加返回值，去掉缓存，如果记录存在返回失败并把原打卡记录返回，如果不存在再直接插入

    public void addRecordIn( int user_id,
                          Time clock_in_time,double locationX,
                            double locationY,Date date) {

        if (date == null) {
            System.out.println("date is null");
            throw new IllegalArgumentException("Date cannot be null");
        }
        else System.out.println("date is not null");

// 根据 user_id 和 date 查询是否已有记录
        RecordPojo existingRecord = recordDao.findByUserIdAndDate(user_id, date);


        // 获取公司设置的上下班时间
        CompanyPojo companySettings = companyDao.getCompanySettings(1);
        if (companySettings == null) {
            System.out.println("companySettings is null");
            throw new IllegalArgumentException("companySettings cannot be null");
        }
        else System.out.println("companySettings is not null");


        String username = userDao.findUsernameById(user_id);
        if (username == null) {
            System.out.println("username not found for user_id: " + user_id);
            throw new IllegalArgumentException("Username cannot be null");
        } else {
            System.out.println("username: " + username);
        }



        Time startTime = companySettings.getStartTime();
        if (startTime == null) {
            System.out.println("startTime is null");
            throw new IllegalArgumentException("startTime cannot be null");
        }
        else System.out.println("startTime is not null");
        if (existingRecord != null) {
            // 如果已经有上班打卡记录，更新 clock_in_time
            existingRecord.setClockInTime(clock_in_time);
            existingRecord.setLocationX(locationX);
            existingRecord.setLocationY(locationY);
            existingRecord.setUsername(username);
            if (clock_in_time.after(startTime)) {
                existingRecord.setStatus("late");  // 如果打卡时间晚于开始时间，标记为迟到
            } else {
                existingRecord.setStatus("on_time");
            }
            recordDao.updateById(existingRecord);
        } else {
            // 如果没有记录，则插入一条新记录
            RecordPojo newRecord = new RecordPojo();
            newRecord.setUserId(user_id);
            newRecord.setClockInTime(clock_in_time);
            newRecord.setLocationX(locationX);
            newRecord.setLocationY(locationY);
            newRecord.setUsername(username);



            System.out.println("clock_in_time: " + clock_in_time);
            System.out.println("startTime: " + startTime);
            System.out.println("date: " + date);

            newRecord.setClockInDate(date);

            if (clock_in_time.after(startTime)) {
                newRecord.setStatus("late");
            } else {
                newRecord.setStatus("on_time");
            }
            recordDao.insert(newRecord);
        }

    }


    public void RecordOut(int user_id, Time clock_out_time,Date date) {
// 根据 user_id 和 date 查询是否已有记录（即是否已经打了上班卡）
        RecordPojo existingRecord = recordDao.findByUserIdAndDate(user_id, date);
        CompanyPojo companySettings = companyDao.getCompanySettings(1);
        Time endTime = companySettings.getEndTime();
        if (existingRecord != null) {
            // 如果存在上班打卡记录
            if (clock_out_time.before(endTime)&&(Objects.equals(existingRecord.getStatus(), "late"))) {
                existingRecord.setStatus("absence");  // 如果下班时间早于结束时间，标记为早退
            } else if(clock_out_time.before(endTime)&&!(Objects.equals(existingRecord.getStatus(), "late"))){
                existingRecord.setStatus("early");
            }
            else existingRecord.setStatus("on_time");
            existingRecord.setClockOutTime(clock_out_time);
            recordDao.updateById(existingRecord);

        } else {
            // 如果没有上班打卡，提示用户先进行上班打卡
            throw new RuntimeException("请先进行上班打卡");
        }
    }

    public List<RecordPojo> getRecordsByUserId(int userId) {
        QueryWrapper<RecordPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        return recordDao.selectList(queryWrapper);
    }

    public List<RecordPojo> getRecordsByDate(String date){

        QueryWrapper<RecordPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clockInDate", date);
        return recordDao.selectList(queryWrapper);


    }



    public List<RecordPojo> getAllRecords(){

        return recordDao.selectList(null);
    }

    public LocalDateTime convertToLocalDateTime(Date date, Time time) {
        return LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
    }

    // 计算单个用户的总工作时长
    public double calculateWorkHours(List<RecordPojo> records) {
        double totalHours = 0;
        for (RecordPojo record : records) {
            if (record.getClockInTime() != null && record.getClockOutTime() != null && record.getClockInDate() != null) {
                LocalDateTime clockIn = convertToLocalDateTime(record.getClockInDate(), record.getClockInTime());
                LocalDateTime clockOut = convertToLocalDateTime(record.getClockInDate(), record.getClockOutTime());
                Duration duration = Duration.between(clockIn, clockOut);
                totalHours += duration.toHours() + (double) duration.toMinutesPart() / 60;
            }
        }
        return totalHours;
    }

    // 获取所有用户的月度工作时间统计
    public List<StatisticsPojo> getMonthlyWorkStats(int year, int month) {
        // 查询某年某月的所有打卡记录
        List<RecordPojo> allRecords = recordDao.findByYearAndMonth(year, month);

        // 将打卡记录按用户分组
        Map<Integer, List<RecordPojo>> recordsByUser = allRecords.stream()
                .collect(Collectors.groupingBy(RecordPojo::getUserId));

        List<StatisticsPojo> statsList = new ArrayList<>();

        // 计算每个用户的总工作时间并创建 StatisticsPojo 对象
        for (Map.Entry<Integer, List<RecordPojo>> entry : recordsByUser.entrySet()) {
            int userId = entry.getKey();
            List<RecordPojo> userRecords = entry.getValue();

            // 计算该用户的总工作时长
            double totalHours = calculateWorkHours(userRecords);

            // 创建并填充 StatisticsPojo 对象
            StatisticsPojo stat = new StatisticsPojo();
            stat.setUser_id(String.valueOf(userId));  // 假设 user_id 是字符串类型
            stat.setTotal_hours(totalHours);
            stat.setMonth(month);
            stat.setYear(year);
            String username = userDao.findUsernameById(userId);
            stat.setUsername(username);
            // 排名可以稍后排序时设置

            statsList.add(stat);
        }

        // 对统计数据按工作时长排序并设置排名
        statsList.sort((s1, s2) -> Double.compare(s2.getTotal_hours(), s1.getTotal_hours()));
        for (int i = 0; i < statsList.size(); i++) {
            statsList.get(i).setRanking(i + 1);
        }

        // 插入或更新统计记录
        for (StatisticsPojo stat : statsList) {
            statisticsDao.upsertStatistics(stat);
        }

        return statsList;
    }


}
