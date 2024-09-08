package com.example.punchcardsystem.controller;


import com.example.punchcardsystem.service.RecordServiceImpl;
import com.example.punchcardsystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@CrossOrigin
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;
    @RequestMapping(value="/ClockIn",method = RequestMethod.POST)
    public String clockIn(@RequestParam("user_id") int userId,
                                          @RequestParam("clock_in_time") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime clockInTime,
                                          @RequestParam("locationX") double locationX,
                                          @RequestParam("locationY") double locationY,
                                          @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        recordService.addRecordIn(userId, Time.valueOf(clockInTime), locationX, locationY, java.sql.Date.valueOf(date));
        return Result.okGetString();
    }

    @RequestMapping(value="/ClockOut",method = RequestMethod.POST)
    public String clockOut(@RequestParam("user_id") int userId,
                          @RequestParam("clock_out_time") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime clockOutTime,
                          @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        recordService.RecordOut(userId, Time.valueOf(clockOutTime), java.sql.Date.valueOf(date));
        return Result.okGetString();
    }



}
