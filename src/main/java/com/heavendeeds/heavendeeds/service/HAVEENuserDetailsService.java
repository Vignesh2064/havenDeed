package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface HAVEENuserDetailsService {

    ResponseEntity<?> createUser(HAVEENuserDetailsDto HAVEENuserDetailsDto, MultipartFile file) throws HeavenApiException;

    ResponseEntity<?> updateUser(HAVEENuserDetailsDto HAVEENuserDetailsDto) throws HeavenApiException;

    ResponseEntity<?> deleteUser(Integer userId) throws HeavenApiException;

    ResponseEntity<?> getAllUser(Pageable pageable) throws HeavenApiException;

    ResponseEntity<?> findByUserId(Integer userId) throws HeavenApiException;

    ResponseEntity<byte[]> findImageById(Integer userId) throws HeavenApiException;

}
