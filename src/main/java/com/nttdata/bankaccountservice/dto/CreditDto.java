package com.nttdata.bankaccountservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
public class CreditDto {

    private final String id;

    private final String cardNumber;
    private final String cvv;
    private final Double balance;
    private final Double creditLine;
    private final LocalDate cardExpirationDate;

    private final Double amountToPay;
    private final Double amountPaid;

    private final LocalDate paymentDate;
    private final String type;
    private final String customerId;

}
