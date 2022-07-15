package com.nttdata.bankaccountservice.util;

import com.nttdata.bankaccountservice.dto.*;
import com.nttdata.bankaccountservice.dto.request.CheckingAccountDto;
import com.nttdata.bankaccountservice.dto.request.FixedTermAccountDto;
import com.nttdata.bankaccountservice.dto.request.SavingsAccountDto;
import com.nttdata.bankaccountservice.model.BankAccount;

public class BankAccountMapper {

    private BankAccountMapper() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static BankAccount toBankAccount(BankAccountDto bankAccountDto) {
        return BankAccount.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .type(bankAccountDto.getType())
                .customerId(bankAccountDto.getCustomerId())
                .maintenanceFee(bankAccountDto.getMaintenanceFee())
                .monthlyMovementLimit(bankAccountDto.getMonthlyMovementLimit())
                .monthlyMinimumBalance(bankAccountDto.getMonthlyMinimumBalance())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccountDto toBankAccountDto(SavingsAccountDto bankAccountDto) {
        return BankAccountDto.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .customerId(bankAccountDto.getCustomerId())
                .monthlyMovementLimit(bankAccountDto.getMonthlyMovementLimit())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccountDto toBankAccountDto(FixedTermAccountDto bankAccountDto) {
        return BankAccountDto.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .customerId(bankAccountDto.getCustomerId())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccountDto toBankAccountDto(CheckingAccountDto bankAccountDto) {
        return BankAccountDto.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .customerId(bankAccountDto.getCustomerId())
                .maintenanceFee(bankAccountDto.getMaintenanceFee())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccount toBankAccount(SavingsAccountDto bankAccountDto) {
        return BankAccount.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .customerId(bankAccountDto.getCustomerId())
                .monthlyMovementLimit(bankAccountDto.getMonthlyMovementLimit())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccount toBankAccount(FixedTermAccountDto bankAccountDto) {
        return BankAccount.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .customerId(bankAccountDto.getCustomerId())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccount toBankAccount(CheckingAccountDto bankAccountDto) {
        return BankAccount.builder()
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .customerId(bankAccountDto.getCustomerId())
                .maintenanceFee(bankAccountDto.getMaintenanceFee())
                .transactionLimit(bankAccountDto.getTransactionLimit())
                .build();
    }

    public static BankAccountDto toBankAccountDto(BankAccount bankAccount) {
        return BankAccountDto.builder()
                .id(bankAccount.getId())
                .accountNumber(bankAccount.getAccountNumber())
                .cci(bankAccount.getCci())
                .balance(bankAccount.getBalance())
                .type(bankAccount.getType())
                .customerId(bankAccount.getCustomerId())
                .maintenanceFee(bankAccount.getMaintenanceFee())
                .monthlyMovementLimit(bankAccount.getMonthlyMovementLimit())
                .monthlyMinimumBalance(bankAccount.getMonthlyMinimumBalance())
                .transactionLimit(bankAccount.getTransactionLimit())
                .build();
    }

}
