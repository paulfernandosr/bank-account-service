package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.CreditDto;
import reactor.core.publisher.Flux;

public interface ICreditService {

    Flux<CreditDto> getCreditsByCustomerId(String customerId);

}
