package com.neilarg.currencybot.dto;

import lombok.Data;

import java.util.List;

@Data
public class ParsedRegionDto {

    private final String name;
    private final List<ParsedCityDto> parsedCities;

}
