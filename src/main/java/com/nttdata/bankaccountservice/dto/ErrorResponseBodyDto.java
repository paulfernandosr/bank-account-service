package com.nttdata.bankaccountservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponseBodyDto {

    private String timestamp;
    private String path;
    private Integer status;
    private String error;
    private String message;
    private List<ValidationDto> validations;

}
