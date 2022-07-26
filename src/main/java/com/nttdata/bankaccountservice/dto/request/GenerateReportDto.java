package com.nttdata.bankaccountservice.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GenerateReportDto {

    private String bankAccountId;
    private LocalDate startDate;
    private LocalDate endDate;

}
