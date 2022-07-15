package com.nttdata.bankaccountservice.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final String FIVE_CURLY_BRACKETS = "{}{}{}{}{}";
    public static final String SLASH = "/";
    public static final String COLON = ": ";
    public static final String IN = " in ";

    // Keys
    public static final String TIMESTAMP = "timestamp";
    public static final String REQUEST_ID = "requestId";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String VALIDATIONS = "validations";
    public static final String ID = "id";
    public static final String ACCOUNT_NUMBER = "accountNumber";
    public static final String CCI = "cci";
    public static final String CUSTOMER_ID = "customerId";

    // Exception messages
    public static final String CUSTOMER_CREDIT_CARD_NOT_FOUND = "There is no credit card for customer with %s: %s";
    public static final String INVALID_CUSTOMER_TYPE = "Invalid type for customer with %s: %s";
    public static final String BANK_ACCOUNT_NOT_FOUND = "Bank account not found with %s: %s";
    public static final String NULL_REQUEST = "The request is null";
    public static final String VIOLATED_CONSTRAINTS = "Restrictions were violated";
    public static final String NOT_NULL = "Must not be null";
    public static final String LESS_THAN_ZERO = "Must not be less than zero";
    public static final String ACCOUNT_DUPLICATED_BY_TWO_FIELDS = "There is already a bank account with %s: %s or %s: %s";
    public static final String ACCOUNT_DUPLICATED_BY_THREE_FIELDS = "There is already a bank account with %s: %s or %s: %s or %s: %s";
    public static final String UTILITY_CLASS = "Utility class";
    public static final String ERROR_RESPONSE_IN_SERVICE = "%s -> %s";

    // Collections
    public static final String BANK_ACCOUNTS_COLLECTION = "bankAccounts";

    // Controller paths
    public static final String BANK_ACCOUNT_CONTROLLER = "/bank-accounts";

    // Method paths
    public static final String GET_ALL_METHOD = "/all";
    public static final String GET_BY_ID_METHOD = "/{" + ID + "}";
    public static final String GET_BY_CUSTOMER_ID_METHOD = "/customers/{" + CUSTOMER_ID + "}";
    public static final String UPDATE_BY_ID_METHOD = "/{" + ID + "}";
    public static final String DELETE_BY_ID_METHOD = "/{" + ID + "}";
    public static final String REGISTER_PERSONAL_SAVINGS_ACCOUNT_METHOD = "/personal-savings-accounts";
    public static final String REGISTER_VIP_PERSONAL_SAVINGS_ACCOUNT_METHOD = "/vip-personal-savings-accounts";
    public static final String REGISTER_PERSONAL_FIXED_TERM_ACCOUNT_METHOD = "/personal-fixed-term-accounts";
    public static final String REGISTER_PERSONAL_CHECKING_ACCOUNT_METHOD = "/personal-checking-accounts";
    public static final String REGISTER_BUSINESS_CHECKING_ACCOUNT_METHOD = "/business-checking-accounts";
    public static final String REGISTER_PYME_BUSINESS_CHECKING_ACCOUNT_METHOD = "/pyme-business-checking-accounts";

    // Customer service
    public static final String CUSTOMER_SERVICE_BASE_URL = "${customerService.baseUrl}";
    public static final String GET_CUSTOMER_BY_ID_METHOD = "${customerService.method.getCustomerById}";

    // Credit service
    public static final String CREDIT_SERVICE_BASE_URL = "${creditService.baseUrl}";
    public static final String GET_CREDITS_BY_CUSTOMER_ID_METHOD = "${creditService.method.getCreditsByCustomerId}";

    public static final String PERSONAL_CUSTOMER = "PERSONAL";
    public static final String BUSINESS_CUSTOMER = "BUSINESS";
    public static final String PERSONAL_SAVINGS_ACCOUNT = "SAVINGS.PERSONAL";
    public static final String VIP_PERSONAL_SAVINGS_ACCOUNT = "SAVINGS.PERSONAL.VIP";
    public static final String PERSONAL_FIXED_TERM_ACCOUNT = "FIXED_TERM.PERSONAL";
    public static final String PERSONAL_CHECKING_ACCOUNT = "CHECKING.PERSONAL";
    public static final String BUSINESS_CHECKING_ACCOUNT = "CHECKING.BUSINESS";
    public static final String PYME_BUSINESS_CHECKING_ACCOUNT = "CHECKING.BUSINESS.PYME";
    public static final String PERSONAL_CREDIT = "CREDIT.PERSONAL";
    public static final String BUSINESS_CREDIT = "CREDIT.BUSINESS";
    public static final String PERSONAL_CREDIT_CARD = "CREDIT_CARD.PERSONAL";
    public static final String BUSINESS_CREDIT_CARD = "CREDIT_CARD.BUSINESS";

}
