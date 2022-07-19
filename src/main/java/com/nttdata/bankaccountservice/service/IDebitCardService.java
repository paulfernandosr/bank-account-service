package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.DebitCardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDebitCardService {

    Flux<DebitCardDto> getAll();

    Mono<DebitCardDto> getById(String id);

    Mono<DebitCardDto> register(DebitCardDto debitCardDto);

    Mono<DebitCardDto> updateById(String id, DebitCardDto debitCardDto);

    Mono<Void> deleteById(String id);

}
