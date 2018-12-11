package com.acer.springbootmicroservice.vo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<CompanyVo> {
    @Override
    public CompanyVo mapRow(ResultSet rs, int rowNum) throws SQLException {

        CompanyVo companyVo = new CompanyVo();
        companyVo.setAid(rs.getString("AID"));
        companyVo.setCode(rs.getString("CODE"));
        companyVo.setName(rs.getString("NAME"));
        companyVo.setShortName(rs.getString("SHORT_NAME"));
        return companyVo;
    }
}
