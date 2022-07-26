package com.nttdata.bankaccountservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class WalletDto {

    private String id;
    private String documentType;
    private String documentNumber;
    private String cellPhoneNumber;
    private String cellPhoneImei;
    private String email;
    private Double balance;

}
