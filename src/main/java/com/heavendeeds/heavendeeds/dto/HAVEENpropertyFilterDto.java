package com.heavendeeds.heavendeeds.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class HAVEENpropertyFilterDto {

    private String city;

    private List<String> bhk;

    private List<String> propertyType;

    private List<String> propertyStatus;

    private Float minPrize;

    private Float maxPrize;

    private Float minSquareFeet;

    private Float maxSquareFeet;

    private int pageNumber;

    private int pageSize;

    private String sort;

}
