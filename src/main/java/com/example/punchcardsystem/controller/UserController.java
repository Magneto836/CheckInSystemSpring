package com.example.punchcardsystem.controller;


import com.example.punchcardsystem.service.UserServiceImpl;
import com.example.punchcardsystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value="/regist",method = RequestMethod.POST)
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("role") String role
                          ){
        //登录
        userService.addUser(username, password, role);

        return Result.okGetString();

    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        // 调用 service 层方法验证用户名和密码
        userService.verifyUserCredentials(username, password);

        // 根据验证结果返回响应
        return Result.okGetString();

    }

}
