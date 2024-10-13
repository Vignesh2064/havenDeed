package com.heavendeeds.heavendeeds.controller;

import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.service.HAVEENSearchService;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class HAVEENSearchController {
    @Autowired
    HAVEENSearchService haveenSearchService;

    @Autowired
    HAVEENpropertyService haveeNpropertyService;

    @GetMapping("searchProperty")
    public ResponseEntity<?> getAllUserDetails(@RequestParam String city,@RequestParam (required = false)String landMark,@RequestParam (required = false)String bhk,@RequestParam Integer pageNo, @RequestParam Integer pageCount) throws HeavenApiException {
        if (landMark != null && landMark.isEmpty()) {
            landMark = null;
        }
        if(bhk!=null && bhk.isEmpty()){
            bhk=null;
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageCount, Sort.by("userId").descending());
        ResponseEntity<?> response = haveenSearchService.advancedSearch(city,landMark,bhk,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getHotCity")
    public ResponseEntity<?> getHotCity() throws HeavenApiException {
        ResponseEntity<?> response = haveenSearchService.getHotCity();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getOtherCity")
    public ResponseEntity<?> getOtherCity() throws HeavenApiException {
        ResponseEntity<?> response = haveenSearchService.getOtherCity();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
