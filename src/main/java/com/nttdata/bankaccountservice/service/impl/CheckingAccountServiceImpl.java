package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.dto.BusinessCheckingAccountDto;
import com.nttdata.bankaccountservice.dto.CheckingAccountDto;
import com.nttdata.bankaccountservice.dto.PersonalCheckingAccountDto;
import com.nttdata.bankaccountservice.exception.BankAccountNotFoundException;
import com.nttdata.bankaccountservice.exception.DuplicateBankAccountException;
import com.nttdata.bankaccountservice.repo.ICheckingAccountRepo;
import com.nttdata.bankaccountservice.service.ICheckingAccountService;
import com.nttdata.bankaccountservice.service.ICustomerService;
import com.nttdata.bankaccountservice.util.BankAccountMapper;
import com.nttdata.bankaccountservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CheckingAccountServiceImpl implements ICheckingAccountService {

    private final ICheckingAccountRepo repo;
    private final ICustomerService customerService;

    @Override
    public Flux<CheckingAccountDto> getAll() {
        return repo.findAll().map(BankAccountMapper::toDto);
    }

    @Override
    public Mono<CheckingAccountDto> getById(String id) {
        return repo.findById(id)
                .map(BankAccountMapper::toDto)
                .switchIfEmpty(Mono.error(new BankAccountNotFoundException(String.format(Constants.BANK_ACCOUNT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<PersonalCheckingAccountDto> registerPersonal(PersonalCheckingAccountDto checkingAccountDto) {
        return customerService.getPersonalCustomerById(checkingAccountDto.getPersonalCustomerId())
                .map(customerDto -> BankAccountMapper.toModel(checkingAccountDto))
                .flatMap(checkingAccount -> repo.existsByAccountNumberOrCciOrPersonalCustomerId(
                                checkingAccount.getAccountNumber(),
                                checkingAccount.getCci(),
                                checkingAccount.getPersonalCustomerId())
                        .flatMap(exists -> {
                            if (Boolean.FALSE.equals(exists)) {
                                return repo.save(checkingAccount.toBuilder().id(null).build()).map(BankAccountMapper::toPersonalDto);
                            } else {
                                PersonalCheckingAccountDto duplicateAccountDto = BankAccountMapper.toPersonalDto(checkingAccount);
                                return Mono.error(new DuplicateBankAccountException(
                                        String.format(Constants.ACCOUNT_DUPLICATED_BY_THREE_FIELDS,
                                                Constants.ACCOUNT_NUMBER, duplicateAccountDto.getAccountNumber(),
                                                Constants.CCI, duplicateAccountDto.getCci(),
                                                Constants.PERSONAL_CUSTOMER_ID, duplicateAccountDto.getPersonalCustomerId())));
                            }
                        }));
    }

    @Override
    public Mono<BusinessCheckingAccountDto> registerBusiness(BusinessCheckingAccountDto checkingAccountDto) {
        return customerService.getBusinessCustomerById(checkingAccountDto.getBusinessCustomerId())
                .map(customerDto -> BankAccountMapper.toModel(checkingAccountDto))
                .flatMap(checkingAccount -> repo.existsByAccountNumberOrCci(
                                checkingAccount.getAccountNumber(),
                                checkingAccount.getCci())
                        .flatMap(exists -> {
                            if (Boolean.FALSE.equals(exists)) {
                                return repo.save(checkingAccount.toBuilder().id(null).build()).map(BankAccountMapper::toBusinessDto);
                            } else {
                                BusinessCheckingAccountDto duplicateAccountDto = BankAccountMapper.toBusinessDto(checkingAccount);
                                return Mono.error(new DuplicateBankAccountException(
                                        String.format(Constants.ACCOUNT_DUPLICATED_BY_TWO_FIELDS,
                                                Constants.ACCOUNT_NUMBER, duplicateAccountDto.getAccountNumber(),
                                                Constants.CCI, duplicateAccountDto.getCci())));
                            }
                        }));
    }

    @Override
    public Mono<CheckingAccountDto> updateById(String id, CheckingAccountDto checkingAccountDto) {
        Mono<CheckingAccountDto> accountDtoReqMono = Mono.just(checkingAccountDto);
        Mono<CheckingAccountDto> accountDtoDbMono = getById(id);
        return accountDtoReqMono.zipWith(accountDtoDbMono, (accountDtoReq, accountDtoDb) ->
                        BankAccountMapper.toModel(accountDtoDb.toBuilder()
                                .accountNumber(accountDtoReq.getAccountNumber())
                                .personalCustomerId(accountDtoReq.getPersonalCustomerId())
                                .businessCustomerId(accountDtoReq.getBusinessCustomerId())
                                .balance(accountDtoReq.getBalance())
                                .build()))
                .flatMap(customer -> repo.save(customer).map(BankAccountMapper::toDto));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(accountDto -> repo.deleteById(id));
    }

}
