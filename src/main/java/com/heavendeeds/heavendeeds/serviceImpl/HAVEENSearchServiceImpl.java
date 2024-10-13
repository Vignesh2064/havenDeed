package com.heavendeeds.heavendeeds.serviceImpl;

import com.heavendeeds.heavendeeds.constant.HAVEENConstant;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.repository.HAVEENpropertyDetailsRepo;
import com.heavendeeds.heavendeeds.service.HAVEENSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HAVEENSearchServiceImpl implements HAVEENSearchService {

    @Autowired
    HAVEENpropertyDetailsRepo haveeNpropertyDetailsRepo;
    @Override
    public ResponseEntity<?> advancedSearch(String city, String localityOrLandMark,String bhk, Pageable pageable) throws HeavenApiException {
        List<HAVEENpropertyDetails>haveeNpropertyDetails;
        haveeNpropertyDetails=haveeNpropertyDetailsRepo.searchProperty(city,localityOrLandMark,bhk,pageable);
        if (haveeNpropertyDetails.isEmpty() && bhk != null && !bhk.isEmpty()) {
            haveeNpropertyDetails = haveeNpropertyDetailsRepo.searchPropertyIgnoreBhk(city, localityOrLandMark, pageable);
        }
        haveeNpropertyDetails.forEach(haveeNpropertyDetails1 -> haveeNpropertyDetails1.setPropertyCardPhoto(null));
        return ResponseEntity.status(HttpStatus.OK).body(haveeNpropertyDetails);
    }

        @Override
        public ResponseEntity<?> getHotCity() throws HeavenApiException {
            List<String>cities=haveeNpropertyDetailsRepo.findAllUniqueCities();
            List<String> hotCities = cities.stream()
                    .filter(HAVEENConstant.HOT_CITY::contains)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(hotCities);
        }



    @Override
    public ResponseEntity<?> getOtherCity() throws HeavenApiException {
        List<String>cities=haveeNpropertyDetailsRepo.findAllUniqueCities();
        List<String> otherCities = cities.stream()
                .filter(city -> !HAVEENConstant.HOT_CITY.contains(city))
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(otherCities);
    }
}
