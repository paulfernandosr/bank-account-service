package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.dto.*;
import com.nttdata.bankaccountservice.dto.request.*;
import com.nttdata.bankaccountservice.exception.EntityNotFoundException;
import com.nttdata.bankaccountservice.exception.DomainException;
import com.nttdata.bankaccountservice.exception.DuplicateEntityException;
import com.nttdata.bankaccountservice.model.BankAccount;
import com.nttdata.bankaccountservice.model.DebitCard;
import com.nttdata.bankaccountservice.repo.IBankAccountRepo;
import com.nttdata.bankaccountservice.service.*;
import com.nttdata.bankaccountservice.util.BankAccountMapper;
import com.nttdata.bankaccountservice.util.Constants;
import com.nttdata.bankaccountservice.util.DebitCardMapper;
import io.vavr.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements IBankAccountService {

    private final IBankAccountRepo repo;
    private final ICustomerService customerService;
    private final ICreditService creditService;
    private final IMovementService movementService;

    private final IDebitCardService debitCardService;

    @Override
    public Flux<BankAccountDto> getAll() {
        return repo.findAll().map(BankAccountMapper::toBankAccountDto);
    }

    @Override
    public Flux<BankAccountDto> getByCustomerId(String customerId) {
        return repo.findByCustomerId(customerId).map(BankAccountMapper::toBankAccountDto);
    }

    @Override
    public Mono<BankAccountDto> getById(String id) {
        return repo.findById(id)
                .map(BankAccountMapper::toBankAccountDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(String.format(Constants.BANK_ACCOUNT_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<BankAccountDto> registerPersonalSavingsAccount(SavingsAccountDto savingsAccountDto) {
        return registerPersonalBankAccount(
                BankAccountMapper.toBankAccountDto(savingsAccountDto),
                Constants.PERSONAL_SAVINGS_ACCOUNT);
    }

    @Override
    public Mono<BankAccountDto> registerPersonalFixedTermAccount(FixedTermAccountDto fixedTermAccountDto) {
        return registerPersonalBankAccount(
                BankAccountMapper.toBankAccountDto(fixedTermAccountDto).toBuilder().monthlyMovementLimit(1).build(),
                Constants.PERSONAL_FIXED_TERM_ACCOUNT);
    }

    @Override
    public Mono<BankAccountDto> registerPersonalCheckingAccount(CheckingAccountDto checkingAccountDto) {
        return registerPersonalBankAccount(BankAccountMapper.toBankAccountDto(checkingAccountDto), Constants.PERSONAL_CHECKING_ACCOUNT);
    }

    @Override
    public Mono<BankAccountDto> registerVipPersonalSavingsAccount(SavingsAccountDto savingsAccountDto) {
        return customerService.getCustomerById(savingsAccountDto.getCustomerId())
                .filter(customerDto -> Constants.PERSONAL_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(savingsAccountDto.getCustomerId()))
                .flatMap(customerDto -> creditService.getCreditsByCustomerId(customerDto.getId()).collectList())
                .filter(credits -> credits.stream().anyMatch(credit -> credit.getType().equals(Constants.PERSONAL_CREDIT_CARD)))
                .switchIfEmpty(throwCustomerCreditCardNotFound(savingsAccountDto.getCustomerId()))
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(savingsAccountDto.getCustomerId()))
                .map(credits -> BankAccountMapper.toBankAccount(savingsAccountDto))
                .filterWhen(bankAccount -> repo.existsByAccountNumberOrCciOrCustomerId(bankAccount.getAccountNumber(), bankAccount.getCci(), bankAccount.getCustomerId()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateBankAccountException(savingsAccountDto.getAccountNumber(), savingsAccountDto.getCci(), savingsAccountDto.getCustomerId()))
                .map(bankAccount -> bankAccount.toBuilder().type(Constants.VIP_PERSONAL_SAVINGS_ACCOUNT).build())
                .flatMap(repo::save)
                .map(BankAccountMapper::toBankAccountDto);
    }

    @Override
    public Mono<BankAccountDto> registerBusinessCheckingAccount(CheckingAccountDto checkingAccountDto) {
        return customerService.getCustomerById(checkingAccountDto.getCustomerId())
                .filter(customerDto -> Constants.BUSINESS_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(checkingAccountDto.getCustomerId()))
                .flatMap(customerDto -> creditService.getCreditsByCustomerId(customerDto.getId()).collectList())
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(checkingAccountDto.getCustomerId()))
                .map(customerDto -> BankAccountMapper.toBankAccount(checkingAccountDto))
                .filterWhen(checkingAccount -> repo.existsByAccountNumberOrCci(checkingAccount.getAccountNumber(), checkingAccount.getCci()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateBankAccountException(checkingAccountDto.getAccountNumber(), checkingAccountDto.getCci()))
                .map(checkingAccount -> checkingAccount.toBuilder().type(Constants.BUSINESS_CHECKING_ACCOUNT).build())
                .flatMap(repo::save)
                .map(BankAccountMapper::toBankAccountDto);
    }

    @Override
    public Mono<BankAccountDto> registerPymeBusinessCheckingAccount(CheckingAccountDto checkingAccountDto) {
        return customerService.getCustomerById(checkingAccountDto.getCustomerId())
                .filter(customerDto -> Constants.BUSINESS_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(checkingAccountDto.getCustomerId()))
                .flatMap(customerDto -> creditService.getCreditsByCustomerId(customerDto.getId()).collectList())
                .filter(credits -> credits.stream().anyMatch(credit -> credit.getType().equals(Constants.BUSINESS_CREDIT_CARD)))
                .switchIfEmpty(throwCustomerCreditCardNotFound(checkingAccountDto.getCustomerId()))
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(checkingAccountDto.getCustomerId()))
                .map(credits -> BankAccountMapper.toBankAccount(checkingAccountDto))
                .filterWhen(bankAccount -> repo.existsByAccountNumberOrCci(bankAccount.getAccountNumber(), bankAccount.getCci()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateBankAccountException(checkingAccountDto.getAccountNumber(), checkingAccountDto.getCci()))
                .map(bankAccount -> bankAccount.toBuilder().type(Constants.PYME_BUSINESS_CHECKING_ACCOUNT).build())
                .flatMap(repo::save)
                .map(BankAccountMapper::toBankAccountDto);
    }

    @Override
    public Mono<BankAccountDto> updateById(String id, BankAccountDto bankAccountDto) {
        return getById(id).map(validBankAccount -> validBankAccount.toBuilder()
                        .accountNumber(bankAccountDto.getAccountNumber())
                        .cci(bankAccountDto.getCci())
                        .balance(bankAccountDto.getBalance())
                        .type(bankAccountDto.getType())
                        .customerId(bankAccountDto.getCustomerId())
                        .build())
                .map(BankAccountMapper::toBankAccount)
                .flatMap(repo::save)
                .map(BankAccountMapper::toBankAccountDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(bankAccount -> repo.deleteById(id));
    }

    @Override
    public Mono<BankAccountDto> generateReport(GenerateReportDto generateReportDto) {
        return this.getById(generateReportDto.getBankAccountId())
                .flatMap(bankAccount -> Mono.zip(Mono.just(bankAccount),
                        customerService.getCustomerById(bankAccount.getCustomerId()),
                        this.getMovementsBetweenDates(generateReportDto).collectList()))
                .map(this::buildReport);
    }

    @Override
    public Mono<DebitCardDto> associateDebitCard(AssociateDebitCardDto associateDebitCardDto) {
        return Mono.zip(this.getById(associateDebitCardDto.getBankAccountId()),
                        debitCardService.getById(associateDebitCardDto.getDebitCardId()))
                .map(this::addSecondaryAccount)
                .flatMap(modifiedDebitCard -> debitCardService.updateById(modifiedDebitCard.getId(), modifiedDebitCard));
    }

    private DebitCardDto addSecondaryAccount(Tuple2<BankAccountDto, DebitCardDto> tuple) {
        String accountId = tuple.getT1().getId();
        DebitCardDto debitCard = tuple.getT2();
        List<String> secondaryAccountIds = debitCard.getSecondaryAccountIds();
        secondaryAccountIds.add(accountId);
        return debitCard.toBuilder().secondaryAccountIds(secondaryAccountIds).build();
    }

    private Flux<MovementDto> getMovementsBetweenDates(GenerateReportDto generateReportDto) {
        return movementService.getMovementsByBankAccountId(generateReportDto.getBankAccountId())
                .filter(movement -> movement.getTimestamp().isBefore(generateReportDto.getEndDate().atStartOfDay())
                        && movement.getTimestamp().isAfter(generateReportDto.getStartDate().atStartOfDay()));
    }

    private BankAccountDto buildReport(Tuple3<BankAccountDto, CustomerDto, List<MovementDto>> tuple) {
        return tuple.getT1().toBuilder()
                .customerId(null)
                .customer(tuple.getT2())
                .movements(tuple.getT3())
                .build();
    }

    private boolean isOverdueDebt(CreditDto credit) {
        return (credit.getType().startsWith(Constants.CREDIT)
                && credit.getAmountPaid() < credit.getAmountToPay()
                && credit.getPaymentDate().isBefore(LocalDate.now()))
                || (credit.getType().startsWith(Constants.CREDIT_CARD))
                && credit.getBalance() < credit.getCreditLine()
                && credit.getPaymentDate().isBefore(LocalDate.now());
    }

    private Mono<BankAccountDto> registerPersonalBankAccount(BankAccountDto bankAccountDto, String type) {
        return customerService.getCustomerById(bankAccountDto.getCustomerId())
                .filter(customerDto -> Constants.PERSONAL_CUSTOMER.equals(customerDto.getType()))
                .switchIfEmpty(throwInvalidCustomerTypeException(bankAccountDto.getCustomerId()))
                .flatMap(customerDto -> creditService.getCreditsByCustomerId(customerDto.getId()).collectList())
                .filter(credits -> credits.stream().noneMatch(this::isOverdueDebt))
                .switchIfEmpty(throwCustomerWithOverdueDebt(bankAccountDto.getCustomerId()))
                .map(hasDebts -> BankAccountMapper.toBankAccount(bankAccountDto))
                .filterWhen(bankAccount -> repo.existsByAccountNumberOrCciOrCustomerId(bankAccount.getAccountNumber(), bankAccount.getCci(), bankAccount.getCustomerId()).map(exists -> !exists))
                .switchIfEmpty(throwDuplicateBankAccountException(bankAccountDto.getAccountNumber(), bankAccountDto.getCci(), bankAccountDto.getCustomerId()))
                .map(bankAccount -> bankAccount.toBuilder().type(type).build())
                .flatMap(repo::save)
                .map(BankAccountMapper::toBankAccountDto);
    }

    private Mono<BankAccount> throwDuplicateBankAccountException(String accountNumber, String cci, String customerId) {
        return Mono.error(new DuplicateEntityException(
                String.format(Constants.ACCOUNT_DUPLICATED_BY_THREE_FIELDS,
                        Constants.ACCOUNT_NUMBER, accountNumber,
                        Constants.CCI, cci,
                        Constants.CUSTOMER_ID, customerId)));
    }

    private Mono<BankAccount> throwDuplicateBankAccountException(String accountNumber, String cci) {
        return Mono.error(new DuplicateEntityException(
                String.format(Constants.ACCOUNT_DUPLICATED_BY_TWO_FIELDS,
                        Constants.ACCOUNT_NUMBER, accountNumber,
                        Constants.CCI, cci)));
    }

    private Mono<CustomerDto> throwInvalidCustomerTypeException(String customerId) {
        return Mono.error(new DomainException(HttpStatus.BAD_REQUEST,
                String.format(Constants.INVALID_CUSTOMER_TYPE,
                        Constants.CUSTOMER_ID, customerId)));
    }

    private Mono<List<CreditDto>> throwCustomerCreditCardNotFound(String customerId) {
        return Mono.error(new DomainException(HttpStatus.BAD_REQUEST,
                String.format(Constants.CUSTOMER_CREDIT_CARD_NOT_FOUND,
                        Constants.CUSTOMER_ID, customerId)));
    }

    private Mono<List<CreditDto>> throwCustomerWithOverdueDebt(String customerId) {
        return Mono.error(new DomainException(HttpStatus.BAD_REQUEST,
                String.format(Constants.CUSTOMER_WITH_OVERDUE_DEBT,
                        Constants.CUSTOMER_ID, customerId)));
    }

}
