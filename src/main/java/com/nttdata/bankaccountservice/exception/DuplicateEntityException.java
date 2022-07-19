package com.nttdata.bankaccountservice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends DomainException {

    public DuplicateEntityException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
