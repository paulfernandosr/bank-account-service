package com.nttdata.bankaccountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementDto {

    private String id;
    private Double amount;
    private LocalDateTime timestamp;
    private String type;
    private String movementId;
    private String sourceBankAccountId;
    private String targetBankAccountId;
    private String creditId;

}
