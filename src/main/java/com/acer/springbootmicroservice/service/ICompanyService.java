package com.acer.springbootmicroservice.service;

import com.acer.springbootmicroservice.vo.CompanyVo;

import java.util.List;

public interface ICompanyService {
    CompanyVo getCompanyByAid(String aid);
    List<CompanyVo> getAllCompany();
    Boolean addCompany(CompanyVo companyVo);
    void updateCompany(CompanyVo companyVo);
    void deleteCompany(String aid);
}
