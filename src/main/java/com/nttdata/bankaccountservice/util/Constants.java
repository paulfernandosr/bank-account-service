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
    public static final String PERSONAL_CUSTOMER_ID = "personalCustomerId";

    // Exception messages
    public static final String BANK_ACCOUNT_NOT_FOUND = "Bank account not found with %s: %s";
    public static final String NULL_REQUEST = "The request is null";
    public static final String VIOLATED_CONSTRAINTS = "Restrictions were violated";
    public static final String NOT_NULL = "Must not be null";
    public static final String CUSTOMER_ID_IS_REQUIRED = "personalCustomerId or businessCustomerId is required";
    public static final String ACCOUNT_DUPLICATED_BY_A_FIELD = "There is already a bank account with %s: %s";
    public static final String ACCOUNT_DUPLICATED_BY_TWO_FIELDS = "There is already a bank account with %s: %s or %s: %s";

    public static final String ACCOUNT_DUPLICATED_BY_THREE_FIELDS = "There is already a bank account with %s: %s or %s: %s or %s: %s";
    public static final String UTILITY_CLASS = "Utility class";

    public static final String ERROR_RESPONSE_IN_SERVICE = "%s -> %s";

    // Collections
    public static final String CHECKING_ACCOUNTS_COLLECTION = "checkingAccounts";
    public static final String FIXED_TERM_ACCOUNTS_COLLECTION = "fixedTermAccounts";
    public static final String SAVING_ACCOUNTS_COLLECTION = "savingAccounts";

    // Controller paths
    public static final String CHECKING_ACCOUNT_CONTROLLER = "${controller.checking-account.base-path}";
    public static final String FIXED_TERM_ACCOUNT_CONTROLLER = "${controller.fixed-term-account.base-path}";
    public static final String SAVING_ACCOUNT_CONTROLLER = "${controller.saving-account.base-path}";

    // Method paths
    public static final String GET_ALL_METHOD = "${controller.method.get-all}";
    public static final String GET_BY_ID_METHOD = "${controller.method.get-by-id}";
    public static final String REGISTER_METHOD = "${controller.method.register}";
    public static final String UPDATE_BY_ID_METHOD = "${controller.method.update-by-id}";
    public static final String DELETE_BY_ID_METHOD = "${controller.method.delete-by-id}";
    public static final String REGISTER_PERSONAL_METHOD = "${controller.checking-account.method.register-personal}";
    public static final String REGISTER_BUSINESS_METHOD = "${controller.checking-account.method.register-business}";

    // Path variables
    public static final String ID_PATH_VARIABLE = "${controller.path-variable.id}";

    // Customer service
    public static final String CUSTOMER_SERVICE_BASE_URL = "${customer-service.base-url}";
    public static final String GET_PERSONAL_CUSTOMER_BY_ID_METHOD = "${customer-service.method.get-personal-customer-by-id}";
    public static final String GET_BUSINESS_CUSTOMER_BY_ID_METHOD = "${customer-service.method.get-business-customer-by-id}";

}
