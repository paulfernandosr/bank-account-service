package com.nttdata.bankaccountservice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateBankAccountException extends DomainException {

    public DuplicateBankAccountException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
