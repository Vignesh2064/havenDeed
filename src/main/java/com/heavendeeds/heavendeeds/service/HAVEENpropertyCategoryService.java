package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HAVEENpropertyCategoryService {

    ResponseEntity<?> findByCategory( String category, String city,Pageable pageable) throws HeavenApiException;

    ResponseEntity<?> propertyTypeCount()throws HeavenApiException;

}
