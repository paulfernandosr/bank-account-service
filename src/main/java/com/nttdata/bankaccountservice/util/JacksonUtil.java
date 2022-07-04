package com.nttdata.bankaccountservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bankaccountservice.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class JacksonUtil {

    private JacksonUtil() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static String objectToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public static <T> T jsonStringToObject(String jsonString, Class<T> className) {
        try {
            return new ObjectMapper().readValue(jsonString, className);
        } catch (JsonProcessingException e) {
            throw new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
