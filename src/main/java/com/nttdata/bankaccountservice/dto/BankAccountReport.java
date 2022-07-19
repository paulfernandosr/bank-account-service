package com.nttdata.bankaccountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountReport {

    private final String id;
    private final String accountNumber;
    private final String cci;
    private final Double balance;
    private final String type;
    private final String maintenanceFee;
    private final Integer monthlyMovementLimit;
    private final Double monthlyMinimumBalance;
    private final Integer transactionLimit;
    private final CustomerDto customer;
    private final List<MovementDto> movements;

}
