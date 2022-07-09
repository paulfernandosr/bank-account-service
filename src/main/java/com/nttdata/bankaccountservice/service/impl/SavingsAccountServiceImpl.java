package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.dto.SavingsAccountDto;
import com.nttdata.bankaccountservice.exception.BankAccountNotFoundException;
import com.nttdata.bankaccountservice.exception.DuplicateBankAccountException;
import com.nttdata.bankaccountservice.repo.ISavingsAccountRepo;
import com.nttdata.bankaccountservice.service.ICustomerService;
import com.nttdata.bankaccountservice.service.ISavingsAccountService;
import com.nttdata.bankaccountservice.util.BankAccountMapper;
import com.nttdata.bankaccountservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SavingsAccountServiceImpl implements ISavingsAccountService {

    private final ISavingsAccountRepo repo;
    private final ICustomerService customerService;

    @Override
    public Flux<SavingsAccountDto> getAll() {
        return repo.findAll().map(BankAccountMapper::toDto);
    }

    @Override
    public Mono<SavingsAccountDto> getById(String id) {
        return repo.findById(id)
                .map(BankAccountMapper::toDto)
                .switchIfEmpty(Mono.error(new BankAccountNotFoundException(String.format(Constants.BANK_ACCOUNT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<SavingsAccountDto> register(SavingsAccountDto savingsAccountDto) {
        Mono<SavingsAccountDto> registeredAccount = Mono.just(BankAccountMapper.toModel(savingsAccountDto))
                .flatMap(account -> repo.existsByAccountNumberOrCci(account.getAccountNumber(), account.getCci())
                        .flatMap(exists -> (!exists)
                                ? repo.save(account.toBuilder().id(null).build()).map(BankAccountMapper::toDto)
                                : Mono.error(new DuplicateBankAccountException(
                                String.format(Constants.ACCOUNT_DUPLICATED_BY_TWO_FIELDS,
                                        Constants.ACCOUNT_NUMBER, account.getAccountNumber(),
                                        Constants.CCI, account.getCci())))));

        return customerService.getPersonalCustomerById(savingsAccountDto.getPersonalCustomerId())
                .flatMap(customerDto -> repo.existsByPersonalCustomerId(customerDto.getId())
                        .flatMap(exists -> (!exists) ? registeredAccount : Mono.error(new DuplicateBankAccountException(
                                String.format(Constants.ACCOUNT_DUPLICATED_BY_A_FIELD, Constants.PERSONAL_CUSTOMER_ID, customerDto.getId())))));
    }

    @Override
    public Mono<SavingsAccountDto> updateById(String id, SavingsAccountDto savingsAccountDto) {
        Mono<SavingsAccountDto> accountDtoReqMono = Mono.just(savingsAccountDto);
        Mono<SavingsAccountDto> accountDtoDbMono = getById(id);
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
