package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.MovementDto;
import reactor.core.publisher.Flux;

public interface IMovementService {

    Flux<MovementDto> getMovementsByBankAccountId(String bankAccountId);

}
