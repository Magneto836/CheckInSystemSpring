package com.example.punchcardsystem.controller;


import com.example.punchcardsystem.pojo.RecordPojo;
import com.example.punchcardsystem.service.RecordServiceImpl;
import com.example.punchcardsystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;
    @RequestMapping(value="/ClockIn",method = RequestMethod.POST)
    public String clockIn(@RequestParam("user_id") int userId,
                                          @RequestParam("clock_in_time") String clockInTimeStr,
                                          @RequestParam("locationX") double locationX,
                                          @RequestParam("locationY") double locationY,
                                          @RequestParam("date") String  dateStr) {
        recordService.addRecordIn(userId, Time.valueOf(LocalTime.parse(clockInTimeStr)), locationX, locationY, java.sql.Date.valueOf(LocalDate.parse(dateStr)));
        return Result.okGetString();
    }

    @RequestMapping(value="/ClockOut",method = RequestMethod.POST)
    public String clockOut(@RequestParam("user_id") int userId,
                          @RequestParam("clock_out_time") String clockOutTimeStr,
                          @RequestParam("date")  String  dateStr) {
        recordService.RecordOut(userId, Time.valueOf(LocalTime.parse(clockOutTimeStr)),java.sql.Date.valueOf(LocalDate.parse(dateStr)));
        return Result.okGetString();
    }

    @GetMapping("/GetRecord/{userId}")
    public String getRecordsByUserId(@PathVariable int userId) {
        List<RecordPojo> records = recordService.getRecordsByUserId(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("records", records);  // 将记录列表放入 Map
        return Result.okGetStringWithData(data);  // 使用你的 Result 类返回数据
    }

    @GetMapping("/GetAllRecords")
    public String getAllRecords() {
        List<RecordPojo> records = recordService.getAllRecords();  // 获取所有记录
        Map<String, Object> data = new HashMap<>();
        data.put("records", records);  // 将记录列表放入 Map

        return Result.okGetStringWithData(data);
    }

    @GetMapping("/dateRecords/{date}")
    public String getRecordsByUserId(@PathVariable String date) {
        List<RecordPojo> records = recordService.getRecordsByDate(date);
        Map<String, Object> data = new HashMap<>();
        data.put("records", records);  // 将记录列表放入 Map
        if(records.isEmpty()){
            data.put("message", "该日无打卡记录");  // 返回一个带消息的 Map
            return Result.okGetStringWithData(data);
        }
        else return Result.okGetStringWithData(data);  // 使用你的 Result 类返回数据
    }




}
