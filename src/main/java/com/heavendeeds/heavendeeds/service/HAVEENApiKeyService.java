package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyFilterDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HAVEENApiKeyService {

    ResponseEntity<?> getApiKek() throws HeavenApiException;
}
