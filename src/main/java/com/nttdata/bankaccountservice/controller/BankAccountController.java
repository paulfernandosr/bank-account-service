package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.dto.BankAccountDto;
import com.nttdata.bankaccountservice.dto.request.CheckingAccountDto;
import com.nttdata.bankaccountservice.dto.request.FixedTermAccountDto;
import com.nttdata.bankaccountservice.dto.request.SavingsAccountDto;
import com.nttdata.bankaccountservice.service.IBankAccountService;
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
@RequestMapping(Constants.BANK_ACCOUNT_CONTROLLER)
public class BankAccountController {

    private final IBankAccountService service;
    private final RequestValidator validator;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<BankAccountDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @GetMapping(Constants.GET_BY_CUSTOMER_ID_METHOD)
    public Mono<ResponseEntity<Flux<BankAccountDto>>> getAll(@PathVariable(Constants.CUSTOMER_ID) String customerId) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getByCustomerId(customerId)));
    }

    @GetMapping(Constants.GET_BY_ID_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> getById(@PathVariable(Constants.ID) String id) {
        return service.getById(id).map(bankAccount -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bankAccount));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_SAVINGS_ACCOUNT_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> registerPersonalSavingsAccount(@RequestBody SavingsAccountDto bankAccount, final ServerHttpRequest request) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.registerPersonalSavingsAccount(bankAccount))
                .map(registeredBankAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredBankAccount.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredBankAccount));
    }

    @PostMapping(Constants.REGISTER_VIP_PERSONAL_SAVINGS_ACCOUNT_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> registerVipPersonalSavingsAccount(@RequestBody SavingsAccountDto bankAccount, final ServerHttpRequest request) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.registerVipPersonalSavingsAccount(bankAccount))
                .map(registeredBankAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredBankAccount.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredBankAccount));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_FIXED_TERM_ACCOUNT_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> registerPersonalFixedTermAccount(@RequestBody FixedTermAccountDto bankAccount, final ServerHttpRequest request) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.registerPersonalFixedTermAccount(bankAccount))
                .map(registeredBankAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredBankAccount.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredBankAccount));
    }

    @PostMapping(Constants.REGISTER_PERSONAL_CHECKING_ACCOUNT_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> registerPersonalCheckingAccount(@RequestBody CheckingAccountDto bankAccount, final ServerHttpRequest request) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.registerPersonalCheckingAccount(bankAccount))
                .map(registeredBankAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredBankAccount.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredBankAccount));
    }

    @PostMapping(Constants.REGISTER_BUSINESS_CHECKING_ACCOUNT_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> registerBusinessCheckingAccount(@RequestBody CheckingAccountDto bankAccount, final ServerHttpRequest request) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.registerBusinessCheckingAccount(bankAccount))
                .map(registeredBankAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredBankAccount.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredBankAccount));
    }

    @PostMapping(Constants.REGISTER_PYME_BUSINESS_CHECKING_ACCOUNT_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> registerPymeBusinessCheckingAccount(@RequestBody CheckingAccountDto bankAccount, final ServerHttpRequest request) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.registerPymeBusinessCheckingAccount(bankAccount))
                .map(registeredBankAccount -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredBankAccount.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredBankAccount));
    }

    @PutMapping(Constants.UPDATE_BY_ID_METHOD)
    public Mono<ResponseEntity<BankAccountDto>> updateById(@PathVariable(Constants.ID) String id, @RequestBody BankAccountDto bankAccount) {
        return validator.validate(bankAccount)
                .flatMap(validatedBankAccount -> service.updateById(id, bankAccount))
                .map(updatedBankAccount -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(updatedBankAccount));
    }

    @DeleteMapping(Constants.DELETE_BY_ID_METHOD)
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable(Constants.ID) String id) {
        return service.deleteById(id).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }

}
