package com.nttdata.bankaccountservice.repo;

import com.nttdata.bankaccountservice.model.CheckingAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ICheckingAccountRepo extends ReactiveMongoRepository<CheckingAccount, String> {

    Mono<Boolean> existsByAccountNumberOrCci(String number, String cci);

    Mono<Boolean> existsByPersonalCustomerId(String personalCustomerId);

    Mono<Boolean> existsByAccountNumberOrCciOrPersonalCustomerId(String accountNumber, String cci, String personalCustomerId);

}
