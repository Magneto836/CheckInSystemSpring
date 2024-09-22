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
    private int  id;
    @TableField(value = "user_id")
    private String user_id;
    @TableField(value = "total_hours")
    private double total_hours;
    @TableField(value = "month")
    private int month;
    @TableField(value = "ranking")
    private int ranking;
    @TableField(value = "year")
    private int year;
    @TableField(value = "username")
    private String username;


    public void setUserId(int userId) {
        this.user_id = String.valueOf(userId);
    }

    public void setTotalHours(double totalHours) {
        this.total_hours = totalHours;
    }
}
