package com.example.punchcardsystem.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("company_settings")

public class CompanyPojo {
    @TableId(value = "id", type = IdType.AUTO)
    private int id ;
    @TableField("locationX")
    private double  locationX;
    @TableField("locationY")
    private double locationY;
    @TableField("startTime")
    private Time startTime;
    @TableField("endTime")
    private Time endTime;


}
