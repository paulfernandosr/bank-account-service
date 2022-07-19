package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.dto.BankAccountDto;
import com.nttdata.bankaccountservice.dto.DebitCardDto;
import com.nttdata.bankaccountservice.dto.request.SavingsAccountDto;
import com.nttdata.bankaccountservice.service.IDebitCardService;
import com.nttdata.bankaccountservice.util.Constants;
import com.nttdata.bankaccountservice.util.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.DEBIT_CARD_CONTROLLER)
public class DebitCardController {

    private final IDebitCardService service;
    private final RequestValidator validator;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<DebitCardDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_SAVINGS_ACCOUNT_METHOD)
    public Mono<ResponseEntity<DebitCardDto>> registerDebitCard(@RequestBody DebitCardDto debitCardDto, final ServerHttpRequest request) {
        return validator.validate(debitCardDto)
                .flatMap(validatedDebitCard -> service.register(debitCardDto))
                .map(registeredDebitCard -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredDebitCard.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredDebitCard));
    }

}
