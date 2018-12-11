package com.acer.springbootmicroservice.service;

import com.acer.springbootmicroservice.dao.ICompanyDao;
import com.acer.springbootmicroservice.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {
    @Autowired
    private ICompanyDao companyDao;

    @Override
    public CompanyVo getCompanyByAid(String aid) {
        return companyDao.getCompanyByAid(aid);
    }

    @Override
    public List<CompanyVo> getAllCompany() {
        return companyDao.getAllCompany();
    }

    @Override
    public synchronized Boolean addCompany(CompanyVo companyVo) {
        if (companyDao.companyExists(companyVo.getCode())) {
            return Boolean.FALSE;
        } else {
            companyDao.addCompany(companyVo);
            return Boolean.TRUE;
        }
    }

    @Override
    public void updateCompany(CompanyVo companyVo) {
        companyDao.updateCompany(companyVo);
    }

    @Override
    public void deleteCompany(String aid) {
        companyDao.deleteCompany(aid);
    }
}
