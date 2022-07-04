package com.nttdata.bankaccountservice.config;

import com.nttdata.bankaccountservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Value(Constants.CUSTOMER_SERVICE_BASE_URL)
    public String customerServiceBaseUrl;

    @Value(Constants.GET_PERSONAL_CUSTOMER_BY_ID_METHOD)
    public String getPersonalCustomerByIdMethod;

    @Value(Constants.GET_BUSINESS_CUSTOMER_BY_ID_METHOD)
    public String getBusinessCustomerByIdMethod;

}
