package com.example.punchcardsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.punchcardsystem.dao.CompanyDao;
import com.example.punchcardsystem.dao.RecordDao;
import com.example.punchcardsystem.pojo.CompanyPojo;
import com.example.punchcardsystem.pojo.RecordPojo;
import com.example.punchcardsystem.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RecordServiceImpl {

    @Autowired
    private RecordDao recordDao;

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


}
