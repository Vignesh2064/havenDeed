package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyDetailsDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HAVEENpropertyPhotoService {

    ResponseEntity<?> uploadPropertyPhoto(Integer propertyId,MultipartFile[] file) throws HeavenApiException;

    List<byte[]> findByPropertyId(Integer propertyId) throws HeavenApiException;
}
