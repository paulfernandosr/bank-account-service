package com.nttdata.bankaccountservice.repo;

import com.nttdata.bankaccountservice.model.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountRepo extends ReactiveMongoRepository<BankAccount, String> {

    Mono<Boolean> existsByAccountNumberOrCci(String accountNumber, String cci);

    Mono<Boolean> existsByAccountNumberOrCciOrCustomerId(String accountNumber, String cci, String customerId);

    Flux<BankAccount> findByCustomerId(String customerId);

}
