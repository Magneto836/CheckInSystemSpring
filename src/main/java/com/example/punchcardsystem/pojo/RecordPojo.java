package com.example.punchcardsystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data  // 包含 @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@TableName("time_records")
public class RecordPojo {
    @TableId(value = "TimeRecordsId", type = IdType.AUTO)  // 明确指定主键的映射和自增策略
    private int timeRecordsId;
    @TableField(value = "userId")
    private int  userId;
    @TableField(value = "clockInTime")
    private Time clockInTime;
    @TableField(value = "clockOutTime")
    private Time clockOutTime;
    @TableField(value = "locationX")
    private double locationX;
    @TableField(value = "locationY")
    private double locationY;
    @TableField(value = "status")
    private String status;
    @TableField(value = "clockInDate")
    private Date clockInDate;
}
