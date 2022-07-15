package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.config.PropertiesConfig;
import com.nttdata.bankaccountservice.dto.CustomerDto;
import com.nttdata.bankaccountservice.exception.DomainException;
import com.nttdata.bankaccountservice.service.ICustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public CustomerServiceImpl(WebClient.Builder webClientBuilder, PropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(propertiesConfig.customerServiceBaseUrl).build();
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackGetCustomerById")
    @TimeLimiter(name = "customerService", fallbackMethod = "fallbackGetCustomerById")
    public Mono<CustomerDto> getCustomerById(String id) {
        return webClient.get().uri(propertiesConfig.getCustomerByIdMethod, id)
                .retrieve()
                .bodyToMono(CustomerDto.class);
    }

    private Mono<CustomerDto> fallbackGetCustomerById(String id, WebClientResponseException e) {
        return Mono.error(new DomainException(e.getStatusCode(), e.getMessage()));
    }

    private Mono<CustomerDto> fallbackGetCustomerById(String id, TimeoutException e) {
        return Mono.error(new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
