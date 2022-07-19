package com.nttdata.bankaccountservice.dto.request;

import lombok.Getter;

@Getter
public class AssociateDebitCardDto {

    private String debitCardId;
    private String bankAccountId;

}
