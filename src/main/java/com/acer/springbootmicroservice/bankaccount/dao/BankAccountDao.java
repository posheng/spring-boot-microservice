package com.acer.springbootmicroservice.bankaccount.dao;

import com.acer.springbootmicroservice.bankaccount.exception.BankTransactionException;
import com.acer.springbootmicroservice.bankaccount.mapper.BankAccountMapper;
import com.acer.springbootmicroservice.bankaccount.model.BankAccountInfo;
import com.acer.springbootmicroservice.vo.CompanyRowMapper;
import com.acer.springbootmicroservice.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BankAccountDao implements IBankAccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BankAccountInfo> getBankAccounts() {
        return jdbcTemplate.query(BankAccountMapper.BASE_SQL, new BankAccountMapper());
    }

    @Override
    public BankAccountInfo findBankAccount(Long id) {
        RowMapper<BankAccountInfo> rowMapper = new BeanPropertyRowMapper<BankAccountInfo>(BankAccountInfo.class);
        try {
            BankAccountInfo bankAccountInfo = jdbcTemplate.queryForObject(BankAccountMapper.BASE_SQL + " WHERE ID = ?", rowMapper, id);
            return bankAccountInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * MANDATORY: Transaction must be created before.
     *
     * @param id
     * @param amount
     * @throws BankTransactionException
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addAmount(Long id, double amount) throws BankTransactionException {
        BankAccountInfo bankAccountInfo = this.findBankAccount(id);
        if (null == bankAccountInfo) {
            throw new BankTransactionException("Account not found " + id);
        }
        double newBalance = bankAccountInfo.getBalance() + amount;
        if (bankAccountInfo.getBalance() + amount < 0) {
            throw new BankTransactionException("The money in the account '" + id + "' is not enough (" + bankAccountInfo.getBalance() + ")");
        }
        bankAccountInfo.setBalance(newBalance);
        String updateSql = "UPDATE Z_BANK_ACCOUNT SET BALANCE = ? WHERE ID = ?";
        jdbcTemplate.update(updateSql, bankAccountInfo.getBalance(), bankAccountInfo.getId());
    }

    /**
     * Do not catch BankTransactionException in this method.
     *
     * @param fromAccountId
     * @param toAccountId
     * @param amount
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
    public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException {
        this.addAmount(toAccountId, amount);
        this.addAmount(fromAccountId, -amount);
    }

    @Override
    public BankAccountInfo getBankAccountById(String id) {
        String sql = "SELECT ID, FULL_NAME, BALANCE FROM AGBS.Z_BANK_ACCOUNT WHERE ID = ?";
        RowMapper<BankAccountInfo> rowMapper = new BeanPropertyRowMapper<BankAccountInfo>(BankAccountInfo.class);
        BankAccountInfo bankAccountInfo = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return bankAccountInfo;
    }

    @Override
    public List<BankAccountInfo> getAllBankAccount() {
        String sql = "SELECT ID, FULL_NAME, BALANCE FROM AGBS.Z_BANK_ACCOUNT";
        RowMapper<BankAccountInfo> rowMapper = new BankAccountMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void addBankAccount(BankAccountInfo bankAccountInfo) {
        // Add Company
        String sql = "INSERT INTO AGBS.Z_BANK_ACCOUNT (ID, FULL_NAME, BALANCE) values (?, ?, ?)";
        jdbcTemplate.update(sql, bankAccountInfo.getId(), bankAccountInfo.getFullName(), bankAccountInfo.getBalance());

        // Fetch article id
        sql = "SELECT ID FROM AGBS.Z_BANK_ACCOUNT WHERE ID = ? AND FULL_NAME=? AND BALANCE=?";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, bankAccountInfo.getId(), bankAccountInfo.getFullName(), bankAccountInfo.getBalance());

        // Set article id
        bankAccountInfo.setId(id);
    }

    @Override
    public void updateBankAccount(BankAccountInfo bankAccountInfo) {
        String sql = "UPDATE AGBS.Z_BANK_ACCOUNT SET FULL_NAME = ?, BALANCE= ? WHERE ID = ?";
        jdbcTemplate.update(sql, bankAccountInfo.getFullName(), bankAccountInfo.getBalance(), bankAccountInfo.getId());
    }

    @Override
    public void deleteBankAccount(String id) {
        String sql = "DELETE FROM AGBS.Z_BANK_ACCOUNT WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Boolean bankAccountExists(Long id) {
        String sql = "SELECT COUNT(*) FROM AGBS.Z_BANK_ACCOUNT WHERE ID = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if(count == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}
