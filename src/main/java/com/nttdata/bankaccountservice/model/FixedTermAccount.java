package com.nttdata.bankaccountservice.model;

import com.nttdata.bankaccountservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder(toBuilder = true)
@Document(collection = Constants.FIXED_TERM_ACCOUNTS_COLLECTION)
public class FixedTermAccount {

    @Id
    private final String id;
    private final String accountNumber;
    private final String cci;
    private final Double balance;
    private final String personalCustomerId;

}
