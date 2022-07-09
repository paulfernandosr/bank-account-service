package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.SavingsAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISavingsAccountService {

    Flux<SavingsAccountDto> getAll();

    Mono<SavingsAccountDto> getById(String id);

    Mono<SavingsAccountDto> register(SavingsAccountDto savingsAccountDto);

    Mono<SavingsAccountDto> updateById(String id, SavingsAccountDto savingsAccountDto);

    Mono<Void> deleteById(String id);

}
