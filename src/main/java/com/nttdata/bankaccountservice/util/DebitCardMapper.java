package com.nttdata.bankaccountservice.util;

import com.nttdata.bankaccountservice.dto.DebitCardDto;
import com.nttdata.bankaccountservice.model.DebitCard;

public class DebitCardMapper {

    private DebitCardMapper() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static DebitCard toDebitCard(DebitCardDto debitCardDto) {
        return DebitCard.builder()
                .id(debitCardDto.getId())
                .cardNumber(debitCardDto.getCardNumber())
                .cvv(debitCardDto.getCvv())
                .expirationDate(debitCardDto.getExpirationDate())
                .mainAccountId(debitCardDto.getMainAccountId())
                .secondaryAccountIds(debitCardDto.getSecondaryAccountIds())
                .walletId(debitCardDto.getWalletId())
                .build();
    }

    public static DebitCardDto toDebitCardDto(DebitCard debitCard) {
        return DebitCardDto.builder()
                .id(debitCard.getId())
                .cardNumber(debitCard.getCardNumber())
                .cvv(debitCard.getCvv())
                .expirationDate(debitCard.getExpirationDate())
                .mainAccountId(debitCard.getMainAccountId())
                .secondaryAccountIds(debitCard.getSecondaryAccountIds())
                .walletId(debitCard.getWalletId())
                .build();
    }

}
