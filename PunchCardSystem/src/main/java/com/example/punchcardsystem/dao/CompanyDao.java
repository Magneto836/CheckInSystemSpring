package com.example.punchcardsystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.punchcardsystem.pojo.CompanyPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CompanyDao extends BaseMapper <CompanyPojo>{

    @Select("SELECT * FROM company_settings WHERE id = #{companyId}")
    CompanyPojo getCompanySettings(int companyId);  // 通过公司 ID 获取公司设置
}
