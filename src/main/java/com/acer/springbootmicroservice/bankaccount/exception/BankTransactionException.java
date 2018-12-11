package com.acer.springbootmicroservice.bankaccount.exception;

public class BankTransactionException extends Exception {

    private static final long serialVersionUID = 7892708109786632148L;

    public BankTransactionException(String message) {
        super(message);
    }
}
