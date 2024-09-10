package com.example.punchcardsystem;

import com.example.punchcardsystem.service.CompanyServiceImpl;
import com.example.punchcardsystem.service.RecordServiceImpl;
import com.example.punchcardsystem.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;

@SpringBootTest
class PunchCardSystemApplicationTests {

	@Autowired
	private CompanyServiceImpl companyService;  // 使用 @Autowired 注入
	@Autowired
	private RecordServiceImpl recordService;
	@Autowired
	private UserServiceImpl userService;

	@Test
	void contextLoads() {
/*
		companyService.updateCompanySettings(1,-10, 20.51,
				Time.valueOf("9:30:00"),Time.valueOf("18:30:00"));

		userService.addUser("Magneto","123456","admin");

		System.out.println(userService.verifyUserCredentials("Magneto","123456"));
		System.out.println(userService.verifyUserCredentials("Magneto","1234567"));

	recordService.addRecordIn(1,Time.valueOf("10:30:00"),0.25,-8.56, Date.valueOf("2023-09-06"));

		userService.addUser("tester1","中文","employee");







		recordService.addRecordIn(2,Time.valueOf("12:30:00"), 108.3,-101.568,Date.valueOf("2024-09-20"));
		*/
		recordService.RecordOut(2,Time.valueOf("13:00:00"),Date.valueOf("2024-09-20"));

	}

}
