package com.example.punchcardsystem.service;


import com.example.punchcardsystem.dao.CompanyDao;
import com.example.punchcardsystem.dao.UserDao;
import com.example.punchcardsystem.pojo.CompanyPojo;
import com.example.punchcardsystem.pojo.UserPojo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;

@Service
@Transactional
public class CompanyServiceImpl  {

    /*
    * 要实现的功能：
    *
    *  添加和修改地址、上下班时间即可（注意管理员权限）（两个函数） √

    * */

    @Autowired
    CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

     /*

     更新数据， id 当然要由系统自动给出，同时注意 company_settings 表中不需要
     有userId , 控制只有管理员端有修改的按钮，就可以不用 userId
     */

    public void addCompanySettings(double locationX,
                                   double locationY, Time start_time , Time end_time
                                  ){

            CompanyPojo companyPojo = new CompanyPojo();
            companyPojo.setLocationX(locationX);
            companyPojo.setLocationY(locationY);
            companyPojo.setStartTime(start_time);
            companyPojo.setEndTime(end_time);

            // 调用 DAO 层的插入方法
            companyDao.insert(companyPojo);

    }
    public void updateCompanySettings(
            int id , double locationX,
            double locationY, Time start_time , Time end_time) {



            CompanyPojo companyPojo = companyDao.selectById(id);
            if (companyPojo == null) {
                throw new RuntimeException("未找到对应的公司设置记录");
            }
            else if(locationX>180||locationX<-180){
                throw new RuntimeException("经度超出范围，请重新设置");
            }
            else if(locationY>180||locationY<-180){
                throw new RuntimeException("维度超出范围，请重新设置");
            }
            else{
                CompanyPojo updatedPojo = new CompanyPojo(id, locationX, locationY, start_time, end_time);
                companyDao.updateById(updatedPojo);  // 执行更新
            }
        }
}






