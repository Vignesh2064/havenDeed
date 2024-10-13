package com.heavendeeds.heavendeeds.controller;

import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.service.HAVEENuserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("haveenUserRegister")
public class HAVEENUserController {
    @Autowired
    HAVEENuserDetailsService HAVEENuserDetailsService;

    @PostMapping(value = "saveUser",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@RequestPart("user") HAVEENuserDetailsDto haveeNuserDetailsDto, @RequestPart("pic") MultipartFile file) throws HeavenApiException {
        ResponseEntity<?> response = HAVEENuserDetailsService.createUser(haveeNuserDetailsDto,file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @GetMapping("getProfilePic/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) throws HeavenApiException {
//         byte[] profile =HAVEENuserDetailsService.findImageById(id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(profile);
//    }

    @GetMapping("getProfilePicAsByte")
    public ResponseEntity<ResponseEntity<byte[]>> getImageAsByte(@RequestParam Integer userId) throws HeavenApiException {
        ResponseEntity<byte[]>response =HAVEENuserDetailsService.findImageById(userId);
//        String base64Images = Base64.getEncoder().encodeToString(profile);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("updateUser")
    public ResponseEntity<?> updateUser(@RequestBody HAVEENuserDetailsDto haveeNuserDetailsDto) throws HeavenApiException {
        ResponseEntity<?> response = HAVEENuserDetailsService.updateUser(haveeNuserDetailsDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam Integer userId) throws HeavenApiException {
        ResponseEntity<?> response = HAVEENuserDetailsService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestParam Integer userId) throws HeavenApiException {
        ResponseEntity<?> response = HAVEENuserDetailsService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getAllUserDetails")
    public ResponseEntity<?> getAllUserDetails(@RequestParam Integer pageNo, @RequestParam Integer pageCount) throws HeavenApiException {
        Pageable pageable = PageRequest.of(pageNo - 1, pageCount, Sort.by("userId").descending());
        ResponseEntity<?> response = HAVEENuserDetailsService.getAllUser(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}