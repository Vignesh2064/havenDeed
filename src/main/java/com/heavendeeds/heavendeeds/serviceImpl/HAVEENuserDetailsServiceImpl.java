package com.heavendeeds.heavendeeds.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavendeeds.heavendeeds.constant.Messages;
import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import com.heavendeeds.heavendeeds.entities.HAVEENuserRoleMap;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.exception.HeavenApiExceptionCode;
import com.heavendeeds.heavendeeds.repository.HAVEENuserDetailsRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENUserRoleMapRepo;
import com.heavendeeds.heavendeeds.response.Response;
import com.heavendeeds.heavendeeds.service.HAVEENuserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class HAVEENuserDetailsServiceImpl implements HAVEENuserDetailsService {

    @Autowired
    HAVEENuserDetailsRepo haveeNuserDetailsRepo;

    @Autowired
    HAVEENUserRoleMapRepo haveenUserRoleMapRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(HAVEENuserDetailsServiceImpl.class);

    @Override
    public ResponseEntity<?> createUser(HAVEENuserDetailsDto  haveeNuserDetailsDto, MultipartFile file) throws HeavenApiException {
        Response<HAVEENuserDetailsDto> response = new Response<>();
        if (Objects.nonNull(haveeNuserDetailsDto) && Objects.isNull(haveeNuserDetailsDto.getUserId())) {
            if (!haveeNuserDetailsRepo.existsByEmail(haveeNuserDetailsDto.getEmail()) && (Objects.nonNull(haveeNuserDetailsDto.getEmail()) && !haveeNuserDetailsDto.getEmail().isEmpty())) {
                try {
                    HAVEENuserDetails haveeNuserDetails = objectMapper.convertValue(haveeNuserDetailsDto, HAVEENuserDetails.class);
                    haveeNuserDetails.setProfilePic(compressImage(file));
                    HAVEENuserDetails haveeNuserDetails1 = haveeNuserDetailsRepo.save(haveeNuserDetails);
                    HAVEENuserRoleMap haveeNuserRoleMap = new HAVEENuserRoleMap();
                    haveeNuserRoleMap.setUserId(haveeNuserDetails1.getUserId());
                    haveeNuserRoleMap.setRoleId(2);
                    haveenUserRoleMapRepo.save(haveeNuserRoleMap);
                    HAVEENuserDetailsDto haveeNuserDetailsDto1 = objectMapper.convertValue(haveeNuserDetails1, HAVEENuserDetailsDto.class);
                    haveeNuserDetailsDto1.setProfilePic(null);
                    logger.info("User details saved successfully. {}", convertObjectToString(haveeNuserDetailsDto1));
                    return ResponseEntity.ok(responseBuilder(HttpStatus.OK.value(), haveeNuserDetailsDto1, Messages.USER_CREATED.getMessages()));
                } catch (Exception e) {
                    logger.error("Error when creating user details: {}", e.getMessage());
                    throw new HeavenApiException(HeavenApiExceptionCode.CREATE_USER_EXCEPTION, new Object[]{e.getMessage()});
                }
            } else {
                logger.error("Given invalid email or email is already exist.");
                throw new HeavenApiException(HeavenApiExceptionCode.INVALID_EMAIL_EXCEPTION, null);
            }
        }
        logger.error("Given invalid input details or no data. {}", convertObjectToString(haveeNuserDetailsDto));
        response.setData(null);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(Messages.INVALID_USER_DATA_REQUEST_TO_ADD.getMessages());
        return ResponseEntity.badRequest().body(response);
    }


    @Override
    public ResponseEntity<?> updateUser(HAVEENuserDetailsDto HAVEENuserDetailsDto) throws HeavenApiException {
        Response<HAVEENuserDetailsDto> response = new Response<>();
        if ((Objects.nonNull(HAVEENuserDetailsDto) && Objects.nonNull(HAVEENuserDetailsDto.getUserId())&&Objects.isNull(HAVEENuserDetailsDto.getProfilePic()))) {
            HAVEENuserDetails HAVEENuserDetails = objectMapper.convertValue(HAVEENuserDetailsDto, HAVEENuserDetails.class);
            HAVEENuserDetails HAVEENuserDetails1 = haveeNuserDetailsRepo.findByUserId(HAVEENuserDetailsDto.getUserId());
            HAVEENuserDetails emailExist = haveeNuserDetailsRepo.findOneByEmail(HAVEENuserDetailsDto.getEmail());
            if (Objects.nonNull(emailExist) && !Objects.equals(emailExist.getUserId(), HAVEENuserDetails1.getUserId())) {
                logger.error("Error when {} trying to update email id for the user with existing user id.", emailExist.getEmail());
                throw new HeavenApiException(HeavenApiExceptionCode.EMAIL_ALREADY_EXIST, null);
            }
            if (Objects.nonNull(HAVEENuserDetails.getHaveeNuserAuthenticationDetails())) {
                logger.error("Authentication is not allowed to update when updating user details");
                throw new HeavenApiException(HeavenApiExceptionCode.AUTH_IS_NOT_ALLOWED_TO_UPDATE, null);
            }
            try {
                HAVEENuserDetails HAVEENuserDetails2 = haveeNuserDetailsRepo.save(HAVEENuserDetails);
                HAVEENuserDetailsDto HAVEENuserDetailsDto1 = objectMapper.convertValue(HAVEENuserDetails2, HAVEENuserDetailsDto.class);
                logger.info("User details updated successfully: {}", convertObjectToString(HAVEENuserDetailsDto1));
                response.setMessage(Messages.USER_DETAILS_UPDATED.getMessages());
                response.setStatus(HttpStatus.OK.value());
                response.setData(HAVEENuserDetailsDto1);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error when updating user details: {}", e.getMessage());
                throw new HeavenApiException(HeavenApiExceptionCode.UPDATE_DETAILS_EXCEPTION, new Object[]{e.getMessage()});
            }
        }
        else{
            logger.error("User {} is not found.",convertObjectToString(HAVEENuserDetailsDto));
            throw  new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION,null);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer userId) throws HeavenApiException {
        Response<HAVEENuserDetailsDto> response = new Response<>();

        if (haveeNuserDetailsRepo.existsByUserId(userId)) {
            try {
                HAVEENuserDetails HAVEENuserDetails = haveeNuserDetailsRepo.findByUserId(userId);
                haveeNuserDetailsRepo.delete(HAVEENuserDetails);
                logger.info("User is deleted successfully. {}", convertObjectToString(HAVEENuserDetails));
                response.setData(null);
                response.setMessage(Messages.USER_DELETED.getMessages());
                response.setStatus(HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error when deleting the user {}", e.getMessage());
                throw new HeavenApiException(HeavenApiExceptionCode.CLEAN_USER_EXCEPTION, new Object[]{e.getMessage()});
            }
        }
        logger.error("User is not found.{}", userId);
        throw new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION, null);
    }

    @Override
    public ResponseEntity<?> getAllUser(Pageable pageable) throws HeavenApiException {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Page<HAVEENuserDetails> haveeNuserDetails = haveeNuserDetailsRepo.findAll(pageable);
            haveeNuserDetails.stream().forEach(user ->user.setProfilePic(null));
            response.put("data", haveeNuserDetails.getContent());
            response.put("totalPages", haveeNuserDetails.getTotalPages());
            response.put("totalRecords", haveeNuserDetails.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error when getting all User details.{}", e.getMessage());
            throw new HeavenApiException(HeavenApiExceptionCode.GET_ALL_USER_EXCEPTION, new Object[]{e.getMessage()});
        }
    }

    @Override
    public ResponseEntity<?> findByUserId(Integer userId) throws HeavenApiException {
        Response<HAVEENuserDetailsDto> response = new Response<>();
        HAVEENuserDetails HAVEENuserDetails = haveeNuserDetailsRepo.findByUserIdOrderByCreatedDtDesc(userId);

        if (Objects.nonNull(HAVEENuserDetails)) {
            try {
                response.setStatus(HttpStatus.OK.value());
                HAVEENuserDetailsDto HAVEENuserDetailsDto = objectMapper.convertValue(HAVEENuserDetails, HAVEENuserDetailsDto.class);
                HAVEENuserDetailsDto.setProfilePic(null);
                HAVEENuserDetailsDto.getHaveeNuserAuthenticationDetails().setOtp(null);
                response.setData(HAVEENuserDetailsDto);
                response.setMessage(Messages.USER_DETAILS_RETRIEVED.getMessages());
                return ResponseEntity.ok(response);
            } catch (IllegalArgumentException e) {
                logger.error("Error when retriving user details {}.", e.getMessage());
                throw new HeavenApiException(HeavenApiExceptionCode.INVALID_INPUT_EXCEPTION, null);
            }
        }
        logger.error("User detail is not found for user {}.", userId);
        throw new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION, null);
    }

    @Override
    public ResponseEntity<byte[]> findImageById(Integer userId) throws HeavenApiException {
        HAVEENuserDetails haveeNuserDetails = haveeNuserDetailsRepo.findByUserId(userId);
        if (Objects.isNull(haveeNuserDetails)){
            logger.error("User details not found for the user");
            throw new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION, null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(haveeNuserDetails.getProfilePic());
    }

    private byte[] compressImage(MultipartFile file) throws HeavenApiException, IOException {

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(0.0f);
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            jpgWriter.setOutput(ios);
            jpgWriter.write(null, new javax.imageio.IIOImage(originalImage, null, null), jpgWriteParam);
        } finally {
            jpgWriter.dispose();
        }

        return baos.toByteArray();
    }

    private Response<?> responseBuilder(Integer status, Object data, String message) {

        Response<Object> response = new Response<>();
        response.setStatus(status);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    private String convertObjectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error when converting the object to string {}", e.getMessage());
            return "Error when object conversion.";
        }
    }
}