package com.acer.springbootmicroservice.bankaccount.controller;

import com.acer.springbootmicroservice.bankaccount.dao.BankAccountDao;
import com.acer.springbootmicroservice.bankaccount.exception.BankTransactionException;
import com.acer.springbootmicroservice.bankaccount.form.SendMoneyForm;
import com.acer.springbootmicroservice.bankaccount.model.BankAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class BankAccountController {
    @Autowired
    private BankAccountDao bankAccountDao;

    @Deprecated
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String showBankAccounts(Model model) {
        List<BankAccountInfo> list = bankAccountDao.getBankAccounts();
        model.addAttribute("accountInfos", list);
        return "accountsPage";
    }

    @Deprecated
    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public String viewSendMoneyPage(Model model) {
        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);
        model.addAttribute("sendMoneyForm", form);
        return "sendMoneyPage";
    }

    @Deprecated
    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {
        System.out.println("Send Money::" + sendMoneyForm.getAmount());
        try {
            bankAccountDao.sendMoney(sendMoneyForm.getFromAccountId(),
                    sendMoneyForm.getToAccountId(),
                    sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/sendMoneyPage";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/bankaccount/{id}")
    public ResponseEntity<Object> getCompanyByAid(@PathVariable("id") String id) {
        BankAccountInfo bankAccountInfo = bankAccountDao.getBankAccountById(id);
        return new ResponseEntity<>(bankAccountInfo, HttpStatus.OK);
    }

    @GetMapping(value = "/bankaccounts")
    public ResponseEntity<Object> getAllCompany() {
        List<BankAccountInfo> bankAccountInfoList = bankAccountDao.getAllBankAccount();
        return new ResponseEntity<>(bankAccountInfoList, HttpStatus.OK);
    }

    @PostMapping(value = "bankaccounts")
    public ResponseEntity<Void> addgetCompany(@RequestBody BankAccountInfo bankAccountInfo, UriComponentsBuilder builder) {
        Boolean flag = Boolean.FALSE;
        if (!bankAccountDao.bankAccountExists(bankAccountInfo.getId())) {
            bankAccountDao.addBankAccount(bankAccountInfo);
            flag = Boolean.TRUE;
        }
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/bankaccount/{id}").buildAndExpand(bankAccountInfo.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "bankaccounts")
    public ResponseEntity<Object> updateCompany(@RequestBody BankAccountInfo bankAccountInfo) {
        bankAccountDao.updateBankAccount(bankAccountInfo);
        return new ResponseEntity<>(bankAccountInfo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/bankaccounts/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") String id) {
        bankAccountDao.deleteBankAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
