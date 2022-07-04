package com.nttdata.bankaccountservice.exception;

import org.springframework.http.HttpStatus;

public class BankAccountNotFoundException extends DomainException {

    public BankAccountNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
