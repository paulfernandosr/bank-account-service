package com.nttdata.bankaccountservice.util;

import com.nttdata.bankaccountservice.dto.ValidationDto;
import com.nttdata.bankaccountservice.exception.BadRequestException;
import com.nttdata.bankaccountservice.exception.NullRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T t) {
        if (t == null) return Mono.error(new NullRequestException(Constants.NULL_REQUEST));
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        if (!constraintViolations.isEmpty()) {
            List<ValidationDto> validations = constraintViolations.stream()
                    .map(this::buildMessage)
                    .collect(Collectors.toList());
            return Mono.error(new BadRequestException(Constants.VIOLATED_CONSTRAINTS, t.getClass().getName(), validations));
        }

        return Mono.just(t);
    }

    private <T> ValidationDto buildMessage(ConstraintViolation<T> constraintViolation) {
        return ValidationDto.builder()
                .field(constraintViolation.getPropertyPath().toString())
                .message(constraintViolation.getMessage())
                .build();
    }

}
