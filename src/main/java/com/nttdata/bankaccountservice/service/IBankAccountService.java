package com.nttdata.bankaccountservice.service;

import com.nttdata.bankaccountservice.dto.BankAccountDto;
import com.nttdata.bankaccountservice.dto.BankAccountReport;
import com.nttdata.bankaccountservice.dto.DebitCardDto;
import com.nttdata.bankaccountservice.dto.request.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {

    Flux<BankAccountDto> getAll();

    Flux<BankAccountDto> getByCustomerId(String customerId);

    Mono<BankAccountDto> getById(String id);

    Mono<BankAccountDto> registerPersonalSavingsAccount(SavingsAccountDto bankAccountDto);

    Mono<BankAccountDto> registerVipPersonalSavingsAccount(SavingsAccountDto bankAccountDto);

    Mono<BankAccountDto> registerPersonalFixedTermAccount(FixedTermAccountDto bankAccountDto);

    Mono<BankAccountDto> registerPersonalCheckingAccount(CheckingAccountDto bankAccountDto);

    Mono<BankAccountDto> registerBusinessCheckingAccount(CheckingAccountDto bankAccountDto);

    Mono<BankAccountDto> registerPymeBusinessCheckingAccount(CheckingAccountDto bankAccountDto);

    Mono<BankAccountDto> updateById(String id, BankAccountDto bankAccountDto);

    Mono<Void> deleteById(String id);

    Mono<BankAccountDto> generateReport(GenerateReportDto generateReportDto);

    Mono<DebitCardDto> associateDebitCard(AssociateDebitCardDto associateDebitCardDto);

}
