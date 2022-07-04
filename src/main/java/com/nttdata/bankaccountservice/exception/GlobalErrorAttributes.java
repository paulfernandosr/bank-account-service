package com.nttdata.bankaccountservice.exception;

import com.nttdata.bankaccountservice.dto.ValidationDto;
import com.nttdata.bankaccountservice.util.Constants;
import com.nttdata.bankaccountservice.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        errorAttributes.remove(Constants.REQUEST_ID);
        errorAttributes.replace(Constants.TIMESTAMP, LocalDateTime.now());

        if (error instanceof DomainException) {
            DomainException domainException = (DomainException) error;
            HttpStatus status = domainException.getStatus();
            errorAttributes.replace(Constants.STATUS, status.value());
            errorAttributes.replace(Constants.ERROR, status.getReasonPhrase());
            errorAttributes.put(Constants.MESSAGE, domainException.getMessage());

            if (domainException instanceof BadRequestException) {
                BadRequestException badRequestException = (BadRequestException) domainException;
                List<ValidationDto> validations = badRequestException.getValidations();
                String validationsString = JacksonUtil.objectToString(validations);
                errorAttributes.put(Constants.VALIDATIONS, validations);
                log.error(Constants.FIVE_CURLY_BRACKETS, Constants.VIOLATED_CONSTRAINTS, Constants.IN,
                        badRequestException.getRequestClass(), Constants.COLON, validationsString);
            } else {
                log.error(error.getMessage());
            }
        } else {
            log.error(error.getMessage());
        }

        return errorAttributes;
    }

}
