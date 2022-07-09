package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.dto.SavingsAccountDto;
import com.nttdata.bankaccountservice.service.ISavingsAccountService;
import com.nttdata.bankaccountservice.util.Constants;
import com.nttdata.bankaccountservice.util.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.SAVINGS_ACCOUNT_CONTROLLER)
public class SavingsAccountController {

    private final ISavingsAccountService service;
    private final RequestValidator validator;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<SavingsAccountDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @GetMapping(Constants.GET_BY_ID_METHOD)
    public Mono<ResponseEntity<SavingsAccountDto>> getById(@PathVariable(Constants.ID_PATH_VARIABLE) String id) {
        return service.getById(id).map(account -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(account));
    }

    @PostMapping(Constants.REGISTER_METHOD)
    public Mono<ResponseEntity<SavingsAccountDto>> register(@RequestBody SavingsAccountDto account, final ServerHttpRequest request) {
        return validator.validate(account)
                .flatMap(validatedAccount -> service.register(account)
                        .map(registeredAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredAccount.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(registeredAccount)));
    }

    @PutMapping(Constants.UPDATE_BY_ID_METHOD)
    public Mono<ResponseEntity<SavingsAccountDto>> updateById(@PathVariable(Constants.ID_PATH_VARIABLE) String id, @RequestBody SavingsAccountDto account) {
        return validator.validate(account)
                .flatMap(validatedAccount -> service.updateById(id, account)
                        .map(updatedAccount -> ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(updatedAccount)));
    }

    @DeleteMapping(Constants.DELETE_BY_ID_METHOD)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable(Constants.ID_PATH_VARIABLE) String id) {
        return service.deleteById(id).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

}