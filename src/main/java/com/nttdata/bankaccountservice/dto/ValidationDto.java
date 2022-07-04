package com.nttdata.bankaccountservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ValidationDto implements Serializable {

    private final String field;
    private final String message;

}
