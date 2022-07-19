package com.nttdata.bankaccountservice.model;

import com.nttdata.bankaccountservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@Document(collection = Constants.DEBIT_CARDS_COLLECTION)
public class DebitCard {

    private final String id;
    private final String cardNumber;
    private final String cvv;
    private final LocalDate expirationDate;
    private final String mainAccountId;
    private final List<String> secondaryAccountIds;

}
