package com.acer.springbootmicroservice.dao;

import com.acer.springbootmicroservice.vo.CompanyVo;

import java.util.List;

public interface ICompanyDao {
    CompanyVo getCompanyByAid(String aid);
    List<CompanyVo> getAllCompany();
    void addCompany(CompanyVo companyVo);
    void updateCompany(CompanyVo companyVo);
    void deleteCompany(String aid);
    Boolean companyExists(String code);
}
