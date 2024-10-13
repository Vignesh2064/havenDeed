package com.heavendeeds.heavendeeds.dto;


import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
@Data
public class HAVEENpropertyDetailsDto {

    private Integer propertyId;

    private String bhk;

    private String propertyStatus;

    private String propertyName;

    private String propertyType;

    private Float prize;

    private Float squareFeet;

    private byte[] propertyCardPhoto;

    private String landMark;

    private String city;

    private Integer userId;

    private LocalDate createdDt;

    private LocalDate modifiedDt;

    private String property;

    private Float perSquareFeet;

    private String fullPrize;

    private Double latitude;

    private Double longitude;

}
