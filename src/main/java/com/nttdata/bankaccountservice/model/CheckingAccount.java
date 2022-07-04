package com.nttdata.bankaccountservice.model;

import com.nttdata.bankaccountservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder(toBuilder = true)
@Document(collection = Constants.CHECKING_ACCOUNTS_COLLECTION)
public class CheckingAccount {

    @Id
    private final String id;
    private final String accountNumber;
    private final String cci;
    private final Double balance;
    private final String personalCustomerId;
    private final String businessCustomerId;

}
