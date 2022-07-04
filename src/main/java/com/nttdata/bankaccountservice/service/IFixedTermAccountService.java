package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.FixedTermAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFixedTermAccountService {

    Flux<FixedTermAccountDto> getAll();

    Mono<FixedTermAccountDto> getById(String id);

    Mono<FixedTermAccountDto> register(FixedTermAccountDto fixedTermAccountDto);

    Mono<FixedTermAccountDto> updateById(String id, FixedTermAccountDto fixedTermAccountDto);

    Mono<Void> deleteById(String id);

}
