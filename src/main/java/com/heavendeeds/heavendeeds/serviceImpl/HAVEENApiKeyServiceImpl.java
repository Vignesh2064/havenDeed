package com.heavendeeds.heavendeeds.serviceImpl;

import com.heavendeeds.heavendeeds.entities.HAVEENsecretKey;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.repository.HAVEENsecretKeyRepo;
import com.heavendeeds.heavendeeds.service.HAVEENApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class HAVEENApiKeyServiceImpl implements HAVEENApiKeyService {

    @Autowired
    HAVEENsecretKeyRepo haveeNsecretKeyRepo;

    @Override
    public ResponseEntity<?> getApiKek() throws HeavenApiException {
        HAVEENsecretKey haveeNsecretKey=haveeNsecretKeyRepo.findBySecretId(1);
        String encodedSecretKey = Base64.getEncoder().encodeToString(haveeNsecretKey.getSecretKey().getBytes());
        haveeNsecretKey.setSecretKey(encodedSecretKey);
        return ResponseEntity.status(HttpStatus.OK).body(haveeNsecretKey);
    }
}
