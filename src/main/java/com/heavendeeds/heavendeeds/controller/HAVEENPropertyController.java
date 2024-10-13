package com.heavendeeds.heavendeeds.controller;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyDetailsDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyService;
import com.heavendeeds.heavendeeds.serviceImpl.HAVEENpropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("haveenProperty")
public class HAVEENPropertyController {
    @Autowired
    HAVEENpropertyService haveeNpropertyService;

    @PostMapping(value = "saveProperty",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@RequestPart("property") HAVEENpropertyDetailsDto haveeNpropertyDetailsDto, @RequestPart("cardImg") MultipartFile file) throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyService.createProperty(haveeNpropertyDetailsDto,file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
//    @GetMapping("getPropertyCardPic/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) throws HeavenApiException {
//        byte[] profile =haveeNpropertyService.findImageById(id);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(profile);
//    }

    @GetMapping("getPropertyPicAsByte")
    public ResponseEntity<ResponseEntity<?>> getImageAsByte(@RequestParam Integer propertyId) throws HeavenApiException {
        ResponseEntity<String>response =haveeNpropertyService.findBPropertyId(propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getUserProperty")
    public ResponseEntity<?> getUserDetails(@RequestParam Integer proprtyId) throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyService.findByUserId(proprtyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getAllProperty")
    public ResponseEntity<?> getAllUserDetails(@RequestParam Integer pageNo, @RequestParam Integer pageCount) throws HeavenApiException {
        Pageable pageable = PageRequest.of(pageNo - 1, pageCount, Sort.by("userId").descending());
        ResponseEntity<?> response = haveeNpropertyService.getAllProperty(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getPresentCities")
    public ResponseEntity<?> getPresentCities() throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyService.getPresentCity();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getBhk")
    public ResponseEntity<?> getPresentBhk() throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyService.getPresentBHK();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAmount() throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyService.updateExisting();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}