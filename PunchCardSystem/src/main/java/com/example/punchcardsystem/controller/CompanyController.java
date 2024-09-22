package com.example.punchcardsystem.controller;


import com.example.punchcardsystem.pojo.CompanyPojo;
import com.example.punchcardsystem.pojo.RecordPojo;
import com.example.punchcardsystem.service.CompanyServiceImpl;
import com.example.punchcardsystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CompanyController {

    @Autowired
    CompanyServiceImpl companyService;

    @RequestMapping(value="/updateCompany",method = RequestMethod.POST)
    public String updateCompany(@RequestParam("id") int id ,
                                @RequestParam("locationx") double locationx,
                                @RequestParam("locationy") double locationy,
                                @RequestParam("start_time")  @DateTimeFormat(pattern = "HH:mm:ss") LocalTime start_time,
                                @RequestParam("end_time")  @DateTimeFormat(pattern = "HH:mm:ss") LocalTime end_time
    ){
        //登录
        companyService.updateCompanySettings(id,locationx,locationy,Time.valueOf(start_time),Time.valueOf(end_time));

        return Result.okGetString();
    }

    @GetMapping("/getCompany/{id}")
    public String getRecordsByUserId(@PathVariable int id) {
        CompanyPojo records = companyService.getCompanyById(id);
        Map<String, Object> data = new HashMap<>();
        data.put("records", records);  // 将记录列表放入 Map
        return Result.okGetStringWithData(data);  // 使用你的 Result 类返回数据
    }






}
