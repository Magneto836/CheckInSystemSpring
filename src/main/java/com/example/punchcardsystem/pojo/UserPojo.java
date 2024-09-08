package com.example.punchcardsystem.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")

public class UserPojo {

    @TableField("id")
    private int id ;
    @TableField("username")
    private String username ;
    @TableField("password")
    private String password ;
    @Getter
    @TableField("role")
    private String role;

}
