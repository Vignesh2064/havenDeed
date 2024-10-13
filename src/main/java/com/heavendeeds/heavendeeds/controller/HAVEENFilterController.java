package com.heavendeeds.heavendeeds.controller;
import com.heavendeeds.heavendeeds.dto.HAVEENpropertyFilterDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.service.HAVEENFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filter")
public class HAVEENFilterController {

    @Autowired
    HAVEENFilterService haveenFilterService;

    @PostMapping("filterAll")
    public ResponseEntity<?> getAllUserDetails(@RequestBody HAVEENpropertyFilterDto haveeNpropertyFilterDto) throws HeavenApiException {
        ResponseEntity<?> response = haveenFilterService.advancedFilter(haveeNpropertyFilterDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}


