package com.nttdata.bankaccountservice.util;

import com.nttdata.bankaccountservice.dto.*;
import com.nttdata.bankaccountservice.model.CheckingAccount;
import com.nttdata.bankaccountservice.model.FixedTermAccount;
import com.nttdata.bankaccountservice.model.SavingAccount;

public class BankAccountMapper {

    private BankAccountMapper() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static CheckingAccount toModel(CheckingAccountDto bankAccountDto) {
        return CheckingAccount.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .personalCustomerId(bankAccountDto.getPersonalCustomerId())
                .businessCustomerId(bankAccountDto.getBusinessCustomerId())
                .build();
    }

    public static CheckingAccountDto toDto(CheckingAccount bankAccountDto) {
        return CheckingAccountDto.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .personalCustomerId(bankAccountDto.getPersonalCustomerId())
                .businessCustomerId(bankAccountDto.getBusinessCustomerId())
                .build();
    }


    public static CheckingAccount toModel(PersonalCheckingAccountDto bankAccountDto) {
        return CheckingAccount.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .personalCustomerId(bankAccountDto.getPersonalCustomerId())
                .build();
    }

    public static CheckingAccount toModel(BusinessCheckingAccountDto bankAccountDto) {
        return CheckingAccount.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .businessCustomerId(bankAccountDto.getBusinessCustomerId())
                .build();
    }

    public static PersonalCheckingAccountDto toPersonalDto(CheckingAccount bankAccount) {
        return PersonalCheckingAccountDto.builder()
                .id(bankAccount.getId())
                .accountNumber(bankAccount.getAccountNumber())
                .cci(bankAccount.getCci())
                .balance(bankAccount.getBalance())
                .personalCustomerId(bankAccount.getPersonalCustomerId())
                .build();
    }

    public static BusinessCheckingAccountDto toBusinessDto(CheckingAccount bankAccount) {
        return BusinessCheckingAccountDto.builder()
                .id(bankAccount.getId())
                .accountNumber(bankAccount.getAccountNumber())
                .cci(bankAccount.getCci())
                .balance(bankAccount.getBalance())
                .businessCustomerId(bankAccount.getBusinessCustomerId())
                .build();
    }

    public static FixedTermAccount toModel(FixedTermAccountDto bankAccountDto) {
        return FixedTermAccount.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .personalCustomerId(bankAccountDto.getPersonalCustomerId())
                .build();
    }

    public static FixedTermAccountDto toDto(FixedTermAccount bankAccount) {
        return FixedTermAccountDto.builder()
                .id(bankAccount.getId())
                .accountNumber(bankAccount.getAccountNumber())
                .cci(bankAccount.getCci())
                .balance(bankAccount.getBalance())
                .personalCustomerId(bankAccount.getPersonalCustomerId())
                .build();
    }

    public static SavingAccount toModel(SavingAccountDto bankAccountDto) {
        return SavingAccount.builder()
                .id(bankAccountDto.getId())
                .accountNumber(bankAccountDto.getAccountNumber())
                .cci(bankAccountDto.getCci())
                .balance(bankAccountDto.getBalance())
                .personalCustomerId(bankAccountDto.getPersonalCustomerId())
                .build();
    }

    public static SavingAccountDto toDto(SavingAccount bankAccount) {
        return SavingAccountDto.builder()
                .id(bankAccount.getId())
                .accountNumber(bankAccount.getAccountNumber())
                .cci(bankAccount.getCci())
                .balance(bankAccount.getBalance())
                .personalCustomerId(bankAccount.getPersonalCustomerId())
                .build();
    }

}
