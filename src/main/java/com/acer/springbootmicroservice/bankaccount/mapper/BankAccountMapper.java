package com.acer.springbootmicroservice.bankaccount.mapper;

import com.acer.springbootmicroservice.bankaccount.model.BankAccountInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountMapper implements RowMapper {
    public static final String BASE_SQL = "SELECT ID, FULL_NAME, BALANCE FROM AGBS.Z_BANK_ACCOUNT";

    @Override
    public BankAccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        BankAccountInfo bankAccountInfo = new BankAccountInfo();
        bankAccountInfo.setId(rs.getLong("ID"));
        bankAccountInfo.setFullName(rs.getString("FULL_NAME"));
        bankAccountInfo.setBalance(rs.getDouble("BALANCE"));
        return bankAccountInfo;
    }
}
