package com.nttdata.bankaccountservice.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreditDto {

    private String id;

    private Double amount;
    private Double interestRate;

    private String cardNumber;
    private String cvv;
    private LocalDate expirationDate;
    private Double balance;
    private Double creditLine;

    private String type;
    private String customerId;

}
