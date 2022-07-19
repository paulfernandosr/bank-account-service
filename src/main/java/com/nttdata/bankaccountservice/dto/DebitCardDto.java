package com.nttdata.bankaccountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bankaccountservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebitCardDto {

    private final String id;

    @NotNull(message = Constants.NOT_NULL)
    private final String cardNumber;

    @NotNull(message = Constants.NOT_NULL)
    private final String cvv;

    @NotNull(message = Constants.NOT_NULL)
    private final LocalDate expirationDate;

    @NotNull(message = Constants.NOT_NULL)
    private final String mainAccountId;

    @NotNull(message = Constants.NOT_NULL)
    private final List<String> secondaryAccountIds;

}
