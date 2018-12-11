package com.acer.springbootmicroservice.dao;

import com.acer.springbootmicroservice.vo.CompanyRowMapper;
import com.acer.springbootmicroservice.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class CompanyDao implements ICompanyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public CompanyVo getCompanyByAid(String aid) {
        String sql = "SELECT AID, CODE, NAME, SHORT_NAME FROM MD_COMPANY WHERE AID = ?";
        RowMapper<CompanyVo> companyVoRowMapper = new BeanPropertyRowMapper<CompanyVo>(CompanyVo.class);
        CompanyVo companyVo = jdbcTemplate.queryForObject(sql, companyVoRowMapper, aid);
        return companyVo;
    }

    @Override
    public List<CompanyVo> getAllCompany() {
        String sql = "SELECT AID, CODE, NAME, SHORT_NAME FROM MD_COMPANY";
        RowMapper<CompanyVo> companyVoRowMapper = new CompanyRowMapper();
        return jdbcTemplate.query(sql, companyVoRowMapper);
    }

    @Override
    public void addCompany(CompanyVo companyVo) {
        // Add Company
        String sql = "INSERT INTO MD_COMPANY (AID, CODE, NAME, SHORT_NAME) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, companyVo.getAid(), companyVo.getCode(), companyVo.getName(), companyVo.getShortName());

        // Fetch article id
        sql = "SELECT AID FROM MD_COMPANY WHERE CODE = ? AND NAME=? AND SHORT_NAME=?";
        String aid = jdbcTemplate.queryForObject(sql, String.class, companyVo.getCode(), companyVo.getName(), companyVo.getShortName());

        // Set article id
        companyVo.setAid(aid);
    }

    @Override
    public void updateCompany(CompanyVo companyVo) {
        String sql = "UPDATE MD_COMPANY SET CODE=?, NAME=?, SHORT_NAME=? WHERE AID=?";
        jdbcTemplate.update(sql, companyVo.getCode(), companyVo.getName(), companyVo.getShortName(), companyVo.getAid());
    }

    @Override
    public void deleteCompany(String aid) {
        String sql = "DELETE FROM MD_COMPANY WHERE AID=?";
        jdbcTemplate.update(sql, aid);
    }

    @Override
    public Boolean companyExists(String code) {
        String sql = "SELECT COUNT(*) FROM MD_COMPANY WHERE CODE=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, code);
        if(count == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}
