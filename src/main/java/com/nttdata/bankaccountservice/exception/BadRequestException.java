package com.nttdata.bankaccountservice.exception;

import com.nttdata.bankaccountservice.dto.ValidationDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BadRequestException extends DomainException {

    private final List<ValidationDto> validations;
    private final String requestClass;

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        validations = null;
        requestClass = null;
    }

    public BadRequestException(String message, String requestClass, List<ValidationDto> validations) {
        super(HttpStatus.BAD_REQUEST, message);
        this.validations = validations;
        this.requestClass = requestClass;
    }

}
