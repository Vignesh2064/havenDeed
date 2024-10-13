package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface HAVEENSearchService {

    ResponseEntity<?> advancedSearch(String city, String localityOrLandMark,String bhk, Pageable pageable) throws HeavenApiException;

    ResponseEntity<?> getHotCity() throws HeavenApiException;

    ResponseEntity<?> getOtherCity() throws HeavenApiException;

}
