package com.heavendeeds.heavendeeds.serviceImpl;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyFilterDto;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.repository.HAVEENpropertyDetailsRepo;
import com.heavendeeds.heavendeeds.service.HAVEENFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HAVEENFilterServiceImpl implements HAVEENFilterService {

    @Autowired
    HAVEENpropertyDetailsRepo haveeNpropertyDetailsRepo;

    @Override
    public ResponseEntity<?> advancedFilter(HAVEENpropertyFilterDto haveeNpropertyFilterDto) throws HeavenApiException {
        Sort sort =null;
        Pageable pageable = null;
        if (Objects.nonNull(haveeNpropertyFilterDto.getSort())) {
            String[] sortParts = haveeNpropertyFilterDto.getSort().split("_");
            String sortField = sortParts[0];
            String direction = sortParts[1];
            sort= Sort.by(Sort.Direction.fromString(direction), sortField);
        }
        if (Objects.nonNull(haveeNpropertyFilterDto.getSort())&&haveeNpropertyFilterDto.getPageSize() == 0) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        } else if (Objects.isNull(haveeNpropertyFilterDto.getSort())&&haveeNpropertyFilterDto.getPageSize()==0) {
            pageable=Pageable.unpaged();
        }
        Float minPrize = haveeNpropertyFilterDto.getMinPrize();
        Float maxPrize = haveeNpropertyFilterDto.getMaxPrize();
        Float minSquareFeet = haveeNpropertyFilterDto.getMinSquareFeet();
        Float maxSquareFeet = haveeNpropertyFilterDto.getMaxSquareFeet();
        Page<HAVEENpropertyDetails> filteredProperties = haveeNpropertyDetailsRepo.filterProperty(
                haveeNpropertyFilterDto.getCity(),
                haveeNpropertyFilterDto.getPropertyType(),
                haveeNpropertyFilterDto.getBhk(),
                haveeNpropertyFilterDto.getPropertyStatus(),
                minPrize,
                maxPrize,
                minSquareFeet,
                maxSquareFeet,
                pageable
        );
        filteredProperties.forEach(data ->data.setPropertyCardPhoto(null));
        return ResponseEntity.status(HttpStatus.OK).body(filteredProperties);
    }
}