package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.dto.BusinessCheckingAccountDto;
import com.nttdata.bankaccountservice.dto.CheckingAccountDto;
import com.nttdata.bankaccountservice.dto.PersonalCheckingAccountDto;
import com.nttdata.bankaccountservice.service.ICheckingAccountService;
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
@RequestMapping(Constants.CHECKING_ACCOUNT_CONTROLLER)
public class CheckingAccountController {

    private final ICheckingAccountService service;
    private final RequestValidator validator;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<CheckingAccountDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @GetMapping(Constants.GET_BY_ID_METHOD)
    public Mono<ResponseEntity<CheckingAccountDto>> getById(@PathVariable(Constants.PATH_ID_VARIABLE) String id) {
        return service.getById(id).map(account -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(account));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_METHOD)
    public Mono<ResponseEntity<PersonalCheckingAccountDto>> registerPersonal(@RequestBody PersonalCheckingAccountDto account, final ServerHttpRequest request) {
        return validator.validate(account)
                .flatMap(validatedAccount -> service.registerPersonal(account)
                        .map(registeredAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredAccount.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(registeredAccount)));
    }

    @PostMapping(Constants.REGISTER_BUSINESS_METHOD)
    public Mono<ResponseEntity<BusinessCheckingAccountDto>> registerBusiness(@RequestBody BusinessCheckingAccountDto account, final ServerHttpRequest request) {
        return validator.validate(account)
                .flatMap(validatedAccount -> service.registerBusiness(account)
                        .map(registeredAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredAccount.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(registeredAccount)));
    }

    @PutMapping(Constants.UPDATE_BY_ID_METHOD)
    public Mono<ResponseEntity<CheckingAccountDto>> updateById(@PathVariable(Constants.PATH_ID_VARIABLE) String id, @RequestBody CheckingAccountDto account) {
        return validator.validate(account)
                .flatMap(validatedAccount -> service.updateById(id, account)
                        .map(updatedAccount -> ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(updatedAccount)));
    }

    @DeleteMapping(Constants.DELETE_BY_ID_METHOD)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable(Constants.PATH_ID_VARIABLE) String id) {
        return service.deleteById(id).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

}
