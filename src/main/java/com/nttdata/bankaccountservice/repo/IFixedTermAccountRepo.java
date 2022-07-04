package com.nttdata.bankaccountservice.repo;

import com.nttdata.bankaccountservice.model.FixedTermAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IFixedTermAccountRepo extends ReactiveMongoRepository<FixedTermAccount, String> {

    Mono<Boolean> existsByAccountNumberOrCci(String number, String cci);

    Mono<Boolean> existsByPersonalCustomerId(String personalCustomerId);

}
