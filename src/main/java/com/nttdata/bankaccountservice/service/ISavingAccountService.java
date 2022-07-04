package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.SavingAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISavingAccountService {

    Flux<SavingAccountDto> getAll();

    Mono<SavingAccountDto> getById(String id);

    Mono<SavingAccountDto> register(SavingAccountDto savingAccountDto);

    Mono<SavingAccountDto> updateById(String id, SavingAccountDto savingAccountDto);

    Mono<Void> deleteById(String id);

}
