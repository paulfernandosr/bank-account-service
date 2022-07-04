package com.nttdata.bankaccountservice.repo;

import com.nttdata.bankaccountservice.model.SavingAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ISavingAccountRepo extends ReactiveMongoRepository<SavingAccount, String> {

    Mono<Boolean> existsByAccountNumberOrCci(String number, String cci);

    Mono<Boolean> existsByPersonalCustomerId(String personalCustomerId);

}
