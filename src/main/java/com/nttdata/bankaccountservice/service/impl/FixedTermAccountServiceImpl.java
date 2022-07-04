package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.dto.FixedTermAccountDto;
import com.nttdata.bankaccountservice.exception.BankAccountNotFoundException;
import com.nttdata.bankaccountservice.exception.DuplicateBankAccountException;
import com.nttdata.bankaccountservice.repo.IFixedTermAccountRepo;
import com.nttdata.bankaccountservice.service.ICustomerService;
import com.nttdata.bankaccountservice.service.IFixedTermAccountService;
import com.nttdata.bankaccountservice.util.BankAccountMapper;
import com.nttdata.bankaccountservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FixedTermAccountServiceImpl implements IFixedTermAccountService {

    private final IFixedTermAccountRepo repo;
    private final ICustomerService customerService;

    @Override
    public Flux<FixedTermAccountDto> getAll() {
        return repo.findAll().map(BankAccountMapper::toDto);
    }

    @Override
    public Mono<FixedTermAccountDto> getById(String id) {
        return repo.findById(id)
                .map(BankAccountMapper::toDto)
                .switchIfEmpty(Mono.error(new BankAccountNotFoundException(String.format(Constants.BANK_ACCOUNT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<FixedTermAccountDto> register(FixedTermAccountDto fixedTermAccountDto) {
        Mono<FixedTermAccountDto> registeredAccount = Mono.just(BankAccountMapper.toModel(fixedTermAccountDto))
                .flatMap(account -> repo.existsByAccountNumberOrCci(account.getAccountNumber(), account.getCci())
                        .flatMap(exists -> (!exists)
                                ? repo.save(account.toBuilder().id(null).build()).map(BankAccountMapper::toDto)
                                : Mono.error(new DuplicateBankAccountException(
                                String.format(Constants.ACCOUNT_DUPLICATED_BY_TWO_FIELDS,
                                        Constants.ACCOUNT_NUMBER, account.getAccountNumber(),
                                        Constants.CCI, account.getCci())))));

        return customerService.getPersonalCustomerById(fixedTermAccountDto.getPersonalCustomerId())
                .flatMap(customerDto -> repo.existsByPersonalCustomerId(customerDto.getId())
                        .flatMap(exists -> (!exists) ? registeredAccount : Mono.error(new DuplicateBankAccountException(
                                String.format(Constants.ACCOUNT_DUPLICATED_BY_A_FIELD, Constants.PERSONAL_CUSTOMER_ID, customerDto.getId())))));
    }

    @Override
    public Mono<FixedTermAccountDto> updateById(String id, FixedTermAccountDto fixedTermAccountDto) {
        Mono<FixedTermAccountDto> accountDtoReqMono = Mono.just(fixedTermAccountDto);
        Mono<FixedTermAccountDto> accountDtoDbMono = getById(id);
        return accountDtoReqMono.zipWith(accountDtoDbMono, (accountDtoReq, accountDtoDb) ->
                        BankAccountMapper.toModel(accountDtoDb.toBuilder()
                                .accountNumber(accountDtoReq.getAccountNumber())
                                .personalCustomerId(accountDtoReq.getPersonalCustomerId())
                                .balance(accountDtoReq.getBalance())
                                .build()))
                .flatMap(customer -> repo.save(customer).map(BankAccountMapper::toDto));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(accountDto -> repo.deleteById(id));
    }

}
