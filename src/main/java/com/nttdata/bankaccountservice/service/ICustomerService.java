package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.BusinessCustomerDto;
import com.nttdata.bankaccountservice.dto.PersonalCustomerDto;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<PersonalCustomerDto> getPersonalCustomerById(String id);

    Mono<BusinessCustomerDto> getBusinessCustomerById(String id);

}
