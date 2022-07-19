package com.nttdata.bankaccountservice.repo;

import com.nttdata.bankaccountservice.model.DebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IDebitCardRepo extends ReactiveMongoRepository<DebitCard, String> {

    Mono<Boolean> existsByCardNumber(String cardNumber);

}
