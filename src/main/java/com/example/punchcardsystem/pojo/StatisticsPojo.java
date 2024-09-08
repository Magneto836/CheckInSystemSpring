package com.example.punchcardsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("monthly_work_stats")

public class StatisticsPojo {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField(value = "user_id")
    private String user_id;
    @TableField(value = "total_hours")
    private double total_hours;
    @TableField(value = "month")
    private int month;
    @TableField(value = "ranking")
    private int ranking;



}
