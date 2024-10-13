package com.heavendeeds.heavendeeds.controller;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyFilterDto;
import com.heavendeeds.heavendeeds.entities.HAVEENsecretKey;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.repository.HAVEENsecretKeyRepo;
import com.heavendeeds.heavendeeds.service.HAVEENFilterService;
import com.heavendeeds.heavendeeds.utils.HAVEENcommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/key")
public class HAVEENSecretKeyController {

    @Autowired
    HAVEENcommonUtils haveeNcommonUtils;

    @Autowired
    HAVEENsecretKeyRepo haveeNsecretKeyRepo;

    @PutMapping("encrypt")
    public ResponseEntity<?> getAllUserDetails() throws Exception {
        ResponseEntity<?> response = haveeNcommonUtils.updateApiKey(1);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getKey")
    public ResponseEntity<?> getKey() throws Exception {
        HAVEENsecretKey response = haveeNsecretKeyRepo.findBySecretId(1);
        response.setOrgKey(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
