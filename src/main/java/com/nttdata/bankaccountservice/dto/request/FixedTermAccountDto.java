package com.nttdata.bankaccountservice.dto.request;

import com.nttdata.bankaccountservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder(toBuilder = true)
public class FixedTermAccountDto {

    @NotNull(message = Constants.NOT_NULL)
    private final String accountNumber;

    @NotNull(message = Constants.NOT_NULL)
    private final String cci;

    @NotNull(message = Constants.NOT_NULL)
    @Min(value = 0, message = Constants.LESS_THAN_ZERO)
    private final Double balance;

    @NotNull(message = Constants.NOT_NULL)
    private final String customerId;

    @NotNull(message = Constants.NOT_NULL)
    private final Integer transactionLimit;

}
