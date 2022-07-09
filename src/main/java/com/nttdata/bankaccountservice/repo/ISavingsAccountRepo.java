package com.nttdata.bankaccountservice.repo;

import com.nttdata.bankaccountservice.model.SavingsAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ISavingsAccountRepo extends ReactiveMongoRepository<SavingsAccount, String> {

    Mono<Boolean> existsByAccountNumberOrCci(String number, String cci);

    Mono<Boolean> existsByPersonalCustomerId(String personalCustomerId);

}
