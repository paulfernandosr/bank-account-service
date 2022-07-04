package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.BusinessCheckingAccountDto;
import com.nttdata.bankaccountservice.dto.CheckingAccountDto;
import com.nttdata.bankaccountservice.dto.PersonalCheckingAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICheckingAccountService {

    Flux<CheckingAccountDto> getAll();

    Mono<CheckingAccountDto> getById(String id);

    Mono<PersonalCheckingAccountDto> registerPersonal(PersonalCheckingAccountDto checkingAccountDto);

    Mono<BusinessCheckingAccountDto> registerBusiness(BusinessCheckingAccountDto checkingAccountDto);

    Mono<CheckingAccountDto> updateById(String id, CheckingAccountDto checkingAccountDto);

    Mono<Void> deleteById(String id);

}
