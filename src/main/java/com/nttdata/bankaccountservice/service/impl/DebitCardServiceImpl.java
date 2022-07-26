package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.dto.BankAccountDto;
import com.nttdata.bankaccountservice.dto.DebitCardDto;
import com.nttdata.bankaccountservice.dto.request.AssociateAccountDto;
import com.nttdata.bankaccountservice.exception.DuplicateEntityException;
import com.nttdata.bankaccountservice.exception.EntityNotFoundException;
import com.nttdata.bankaccountservice.repo.IDebitCardRepo;
import com.nttdata.bankaccountservice.service.IBankAccountService;
import com.nttdata.bankaccountservice.service.IDebitCardService;
import com.nttdata.bankaccountservice.util.Constants;
import com.nttdata.bankaccountservice.util.DebitCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DebitCardServiceImpl implements IDebitCardService {

    private final IDebitCardRepo repo;
    private final IBankAccountService bankAccountService;

    @Override
    public Flux<DebitCardDto> getAll() {
        return repo.findAll().map(DebitCardMapper::toDebitCardDto);
    }

    @Override
    public Mono<DebitCardDto> getById(String id) {
        return repo.findById(id)
                .map(DebitCardMapper::toDebitCardDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(String.format(Constants.DEBIT_CARD_NOT_FOUND, Constants.ID, id))));
    }

    @Override
    public Mono<DebitCardDto> register(DebitCardDto debitCardDto) {
        return Mono.zip(bankAccountService.getById(debitCardDto.getMainAccountId()),
                        Flux.fromIterable(debitCardDto.getSecondaryAccountIds()).flatMap(bankAccountService::getById).collectList())
                .filterWhen(tuple -> repo.existsByCardNumber(debitCardDto.getCardNumber()).map(exists -> !exists))
                .switchIfEmpty(Mono.error(new DuplicateEntityException(String.format(Constants.DEBIT_CARD_NOT_FOUND, Constants.CARD_NUMBER, debitCardDto.getCardNumber()))))
                .map(tuple -> DebitCardMapper.toDebitCard(debitCardDto))
                .flatMap(repo::save)
                .map(DebitCardMapper::toDebitCardDto);
    }

    @Override
    public Mono<DebitCardDto> updateById(String id, DebitCardDto debitCardDto) {
        return getById(id).map(existingDebitCard -> updateDebitCardFields(existingDebitCard, debitCardDto))
                .map(DebitCardMapper::toDebitCard)
                .flatMap(repo::save)
                .map(DebitCardMapper::toDebitCardDto);
    }

    private DebitCardDto updateDebitCardFields(DebitCardDto existingDebitCard, DebitCardDto modifiedDebitCard) {
        return existingDebitCard.toBuilder()
                .id(modifiedDebitCard.getId())
                .cardNumber(modifiedDebitCard.getCardNumber())
                .cvv(modifiedDebitCard.getCvv())
                .expirationDate(modifiedDebitCard.getExpirationDate())
                .mainAccountId(modifiedDebitCard.getMainAccountId())
                .secondaryAccountIds(modifiedDebitCard.getSecondaryAccountIds())
                .build();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return getById(id).flatMap(debitCard -> repo.deleteById(id));
    }

    @Override
    public Mono<DebitCardDto> associateAccount(String id, AssociateAccountDto associateAccountDto) {
        return Mono.zip(bankAccountService.getById(associateAccountDto.getBankAccountId()),
                        this.getById(id))
                .map(this::addSecondaryAccount)
                .flatMap(modifiedDebitCard -> this.updateById(modifiedDebitCard.getId(), modifiedDebitCard));
    }

    private DebitCardDto addSecondaryAccount(Tuple2<BankAccountDto, DebitCardDto> tuple) {
        String accountId = tuple.getT1().getId();
        DebitCardDto debitCard = tuple.getT2();
        List<String> secondaryAccountIds = debitCard.getSecondaryAccountIds();
        secondaryAccountIds.add(accountId);
        return debitCard.toBuilder().secondaryAccountIds(secondaryAccountIds).build();
    }

}
