package com.nttdata.bankaccountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bankaccountservice.util.Constants;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckingAccountDto {

    private final String id;

    @NotNull(message = Constants.NOT_NULL)
    private final String accountNumber;

    @NotNull(message = Constants.NOT_NULL)
    private final String cci;

    @NotNull(message = Constants.NOT_NULL)
    private final Double balance;

    private final String personalCustomerId;
    private final String businessCustomerId;

    @AssertTrue(message = Constants.CUSTOMER_ID_IS_REQUIRED)
    private boolean isPersonalCustomerId() {
        return isPersonalCustomerIdOrBusinessCustomerId();
    }

    @AssertTrue(message = Constants.CUSTOMER_ID_IS_REQUIRED)
    private boolean isBusinessCustomerId() {
        return isPersonalCustomerIdOrBusinessCustomerId();
    }

    private boolean isPersonalCustomerIdOrBusinessCustomerId() {
        return (personalCustomerId != null && businessCustomerId == null)
                || (personalCustomerId == null && businessCustomerId != null);
    }

}
