package com.example.punchcardsystem.controller;

import com.example.punchcardsystem.pojo.StatisticsPojo;
import com.example.punchcardsystem.service.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
public class StatisticsController {
    @Autowired
    private RecordServiceImpl recordService;

    @GetMapping("/getMonthRecord/{year}/{month}")
    public ResponseEntity<List<StatisticsPojo>> generateAndSaveMonthlyStats(@PathVariable int year, @PathVariable int month) {
        List<StatisticsPojo> stats = recordService.calculateAndSaveMonthlyWorkStats(year, month);
        return ResponseEntity.ok(stats);  // 返回计算后的统计数据
    }


}
