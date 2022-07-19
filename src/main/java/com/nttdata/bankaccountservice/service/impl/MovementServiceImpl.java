package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.config.PropertiesConfig;
import com.nttdata.bankaccountservice.dto.CreditDto;
import com.nttdata.bankaccountservice.dto.MovementDto;
import com.nttdata.bankaccountservice.exception.DomainException;
import com.nttdata.bankaccountservice.service.IMovementService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeoutException;

@Service
public class MovementServiceImpl implements IMovementService {

    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public MovementServiceImpl(WebClient.Builder webClientBuilder, PropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(propertiesConfig.movementServiceBaseUrl).build();
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    @CircuitBreaker(name = "movementService", fallbackMethod = "fallbackGetMovementsByBankAccountId")
    @TimeLimiter(name = "movementService", fallbackMethod = "fallbackGetMovementsByBankAccountId")
    public Flux<MovementDto> getMovementsByBankAccountId(String bankAccountId) {
        return webClient.get().uri(propertiesConfig.getMovementsByBankAccountIdMethod, bankAccountId)
                .retrieve()
                .bodyToFlux(MovementDto.class);
    }

    private Flux<MovementDto> fallbackGetMovementsByBankAccountId(String bankAccountId, WebClientResponseException e) {
        return Flux.error(new DomainException(e.getStatusCode(), e.getMessage()));
    }

    private Flux<MovementDto> fallbackGetMovementsByBankAccountId(String bankAccountId, TimeoutException e) {
        return Flux.error(new DomainException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
