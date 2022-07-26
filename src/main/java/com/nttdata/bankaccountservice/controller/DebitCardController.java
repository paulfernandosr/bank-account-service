package com.nttdata.bankaccountservice.controller;

import com.nttdata.bankaccountservice.dto.DebitCardDto;
import com.nttdata.bankaccountservice.dto.request.AssociateAccountDto;
import com.nttdata.bankaccountservice.service.IDebitCardService;
import com.nttdata.bankaccountservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.DEBIT_CARD_CONTROLLER)
public class DebitCardController {

    private final IDebitCardService service;

    @GetMapping(Constants.GET_ALL_METHOD)
    public Mono<ResponseEntity<Flux<DebitCardDto>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @PostMapping(Constants.REGISTER_DEBIT_CARD_METHOD)
    public Mono<ResponseEntity<DebitCardDto>> registerDebitCard(@Valid  @RequestBody DebitCardDto debitCardDto, final ServerHttpRequest request) {
        return service.register(debitCardDto)
                .map(registeredDebitCard -> ResponseEntity.created(URI.create(request.getURI() + Constants.SLASH + registeredDebitCard.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registeredDebitCard));
    }

    @PutMapping(Constants.ASSOCIATE_BANK_ACCOUNT_METHOD)
    public Mono<ResponseEntity<DebitCardDto>> associateAccount(@PathVariable(Constants.ID) String id, @Valid @RequestBody AssociateAccountDto associateAccount, final ServerHttpRequest request) {
        return service.associateAccount(id, associateAccount)
                .map(updatedDebitCard -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(updatedDebitCard));
    }

}
