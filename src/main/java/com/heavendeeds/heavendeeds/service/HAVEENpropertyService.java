package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyDetailsDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HAVEENpropertyService {
    ResponseEntity<?> createProperty(HAVEENpropertyDetailsDto haveeNpropertyDetailsDto, MultipartFile file) throws HeavenApiException;

    ResponseEntity<?> updateProperty(HAVEENpropertyDetailsDto haveeNpropertyDetailsDto) throws HeavenApiException;

    ResponseEntity<?> deleteProperty(Integer userId) throws HeavenApiException;

    ResponseEntity<?> getAllProperty(Pageable pageable) throws HeavenApiException;

    ResponseEntity<?> findByUserId(Integer userId) throws HeavenApiException;

    ResponseEntity<String> findBPropertyId(Integer propertyId) throws HeavenApiException;

    ResponseEntity<List<String>> getPresentCity() throws HeavenApiException;

    ResponseEntity<List<String>> getPresentBHK() throws HeavenApiException;

    public ResponseEntity<?> updateExisting() throws HeavenApiException ;
}
