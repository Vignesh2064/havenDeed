package com.heavendeeds.heavendeeds.serviceImpl;
import com.heavendeeds.heavendeeds.constant.HAVEENConstant;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.repository.HAVEENpropertyDetailsRepo;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HAVEENpropertyCategoryServiceImpl implements HAVEENpropertyCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(HAVEENpropertyCategoryServiceImpl.class);
    @Autowired
    HAVEENpropertyDetailsRepo haveeNpropertyDetailsRepo;
    @Override
    public ResponseEntity<?> findByCategory(String category, String city,Pageable pageable ) throws HeavenApiException {

        List<HAVEENpropertyDetails> haveeNpropertyDetails=haveeNpropertyDetailsRepo.findByCategoryAndCity(category,city,pageable);

        haveeNpropertyDetails.forEach(haveeNpropertyDetails1 -> haveeNpropertyDetails1.setPropertyCardPhoto(null));

        return ResponseEntity.status(HttpStatus.OK).body(haveeNpropertyDetails);
    }

    @Override
    public ResponseEntity<?> propertyTypeCount() throws HeavenApiException {

        List<Object[]> objects = haveeNpropertyDetailsRepo.countPropertiesByType();
        Map<String, Long> propertyCountMap = new LinkedHashMap<>();
        Map<String, Long> tempMap = new HashMap<>();
        objects.forEach(objects1 -> {
            String propertyType = (String) objects1[0];
            Long count = (Long) objects1[1];
            tempMap.put(propertyType, count);
        });
        List<String> order = HAVEENConstant.categoryOrder;
        for (String category : order) {
            if (tempMap.containsKey(category)) {
                propertyCountMap.put(category, tempMap.get(category));
            }
        }
        propertyCountMap.forEach((propertyType, count) -> logger.info("Property Type: " + propertyType + ", Count: " + count));
        return ResponseEntity.status(HttpStatus.OK).body(propertyCountMap);
    }
}
