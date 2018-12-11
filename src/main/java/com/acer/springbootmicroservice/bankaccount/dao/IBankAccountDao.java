package com.acer.springbootmicroservice.bankaccount.dao;

import com.acer.springbootmicroservice.bankaccount.exception.BankTransactionException;
import com.acer.springbootmicroservice.bankaccount.model.BankAccountInfo;
import com.acer.springbootmicroservice.vo.CompanyVo;

import java.util.List;

public interface IBankAccountDao {
    List<BankAccountInfo> getBankAccounts();
    BankAccountInfo findBankAccount(Long id);
    void addAmount(Long id, double amount) throws BankTransactionException;
    void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException;

    BankAccountInfo getBankAccountById(String id);
    List<BankAccountInfo> getAllBankAccount();
    void addBankAccount(BankAccountInfo bankAccountInfo);
    void updateBankAccount(BankAccountInfo bankAccountInfo);
    void deleteBankAccount(String id);
    Boolean bankAccountExists(Long id);
}
