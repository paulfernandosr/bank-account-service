package com.nttdata.bankaccountservice.service.impl;

import com.nttdata.bankaccountservice.config.PropertiesConfig;
import com.nttdata.bankaccountservice.dto.BusinessCustomerDto;
import com.nttdata.bankaccountservice.dto.ErrorResponseBodyDto;
import com.nttdata.bankaccountservice.dto.PersonalCustomerDto;
import com.nttdata.bankaccountservice.exception.DomainException;
import com.nttdata.bankaccountservice.service.ICustomerService;
import com.nttdata.bankaccountservice.util.Constants;
import com.nttdata.bankaccountservice.util.JacksonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public CustomerServiceImpl(WebClient.Builder webClientBuilder, PropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(propertiesConfig.customerServiceBaseUrl).build();
        this.propertiesConfig = propertiesConfig;
    }

    public Mono<PersonalCustomerDto> getPersonalCustomerById(String id) {
        return webClient.get().uri(propertiesConfig.getPersonalCustomerByIdMethod, id).retrieve()
                .bodyToMono(PersonalCustomerDto.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new DomainException(e.getStatusCode(), String.format(Constants.ERROR_RESPONSE_IN_SERVICE,
                                e.getMessage(), JacksonUtil.jsonStringToObject(e.getResponseBodyAsString(), ErrorResponseBodyDto.class).getMessage()))));
    }

    public Mono<BusinessCustomerDto> getBusinessCustomerById(String id) {
        return webClient.get().uri(propertiesConfig.getBusinessCustomerByIdMethod, id).retrieve()
                .bodyToMono(BusinessCustomerDto.class)
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new DomainException(e.getStatusCode(), String.format(Constants.ERROR_RESPONSE_IN_SERVICE,
                                e.getMessage(), JacksonUtil.jsonStringToObject(e.getResponseBodyAsString(), ErrorResponseBodyDto.class).getMessage()))));
    }

}
