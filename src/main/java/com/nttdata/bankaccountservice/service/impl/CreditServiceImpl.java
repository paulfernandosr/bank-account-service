package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.config.PropertiesConfig;
import com.nttdata.bankaccountservice.dto.CreditDto;
import com.nttdata.bankaccountservice.exception.DomainException;
import com.nttdata.bankaccountservice.service.ICreditService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeoutException;

@Service
public class CreditServiceImpl implements ICreditService {

    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public CreditServiceImpl(WebClient.Builder webClientBuilder, PropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(propertiesConfig.creditServiceBaseUrl).build();
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    @CircuitBreaker(name = "creditService", fallbackMethod = "fallbackGetCreditsByCustomerId")
    @TimeLimiter(name = "creditService", fallbackMethod = "fallbackGetCreditsByCustomerId")
    public Flux<CreditDto> getCreditsByCustomerId(String customerId) {
        return webClient.get().uri(propertiesConfig.getCreditsByCustomerIdMethod, customerId)
                .retrieve()
                .bodyToFlux(CreditDto.class);
    }

    private Flux<CreditDto> fallbackGetCreditsByCustomerId(String customerId, WebClientResponseException e) {
        return Flux.error(new DomainException(e.getStatusCode(), e.getMessage()));
    }

    private Flux<CreditDto> fallbackGetCreditsByCustomerId(String customerId, TimeoutException e) {
        return Flux.error(new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}

