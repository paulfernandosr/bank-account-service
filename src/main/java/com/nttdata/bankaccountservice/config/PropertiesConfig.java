package com.nttdata.bankaccountservice.config;

import com.nttdata.bankaccountservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Value(Constants.CUSTOMER_SERVICE_BASE_URL)
    public String customerServiceBaseUrl;

    @Value(Constants.GET_CUSTOMER_BY_ID_METHOD)
    public String getCustomerByIdMethod;

    @Value(Constants.CREDIT_SERVICE_BASE_URL)
    public String creditServiceBaseUrl;

    @Value(Constants.GET_CREDITS_BY_CUSTOMER_ID_METHOD)
    public String getCreditsByCustomerIdMethod;

    @Value(Constants.MOVEMENT_SERVICE_BASE_URL)
    public String movementServiceBaseUrl;

    @Value(Constants.GET_MOVEMENTS_BY_BANK_ACCOUNT_ID_METHOD)
    public String getMovementsByBankAccountIdMethod;

}
