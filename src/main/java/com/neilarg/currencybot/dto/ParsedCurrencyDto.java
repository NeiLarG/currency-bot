package com.neilarg.currencybot.dto;

import lombok.Data;

@Data
public class ParsedCurrencyDto {

    private final String bankName;
    private final String currencyCode;
    private final Double purchase;
    private final Double sale;

}
