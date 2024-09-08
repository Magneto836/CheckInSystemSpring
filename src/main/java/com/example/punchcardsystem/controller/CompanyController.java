package com.example.punchcardsystem.controller;


import com.example.punchcardsystem.service.CompanyServiceImpl;
import com.example.punchcardsystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;

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


}
