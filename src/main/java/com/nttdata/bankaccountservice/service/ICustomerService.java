package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<CustomerDto> getCustomerById(String id);

}
