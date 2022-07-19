package com.nttdata.bankaccountservice.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
