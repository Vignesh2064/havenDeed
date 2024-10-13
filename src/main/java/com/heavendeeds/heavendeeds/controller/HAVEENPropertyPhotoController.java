package com.heavendeeds.heavendeeds.controller;

import com.heavendeeds.heavendeeds.dto.HAVEENpropertyPhotoDto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("propertyPhoto")
public class HAVEENPropertyPhotoController {

    @Autowired
    HAVEENpropertyPhotoService haveeNpropertyPhotoService;
    @PostMapping(value = "savePropertyPhoto",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savePhoto(@RequestPart("property") HAVEENpropertyPhotoDto haveeNpropertyPhotoDto , @RequestPart("PropertyImg") MultipartFile[] file) throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyPhotoService.uploadPropertyPhoto(haveeNpropertyPhotoDto.getPropertyId(),file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @GetMapping("getPropertyPhotos")
//    public ResponseEntity<List<String>> getPropertyImages(@RequestParam Integer propertyId) throws HeavenApiException {
//        List<byte[]> profile =haveeNpropertyPhotoService.findByPropertyId(propertyId);
//        List<String> base64Images = profile.stream()
//                .map(image -> Base64.getEncoder().encodeToString(image))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(base64Images);
//    }
}