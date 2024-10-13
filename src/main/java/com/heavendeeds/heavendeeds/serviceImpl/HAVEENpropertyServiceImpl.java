package com.heavendeeds.heavendeeds.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavendeeds.heavendeeds.constant.HAVEENConstant;
import com.heavendeeds.heavendeeds.constant.Messages;
import com.heavendeeds.heavendeeds.dto.HAVEENpropertyDetailsDto;
import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.exception.HeavenApiExceptionCode;
import com.heavendeeds.heavendeeds.repository.HAVEENpropertyDetailsRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENuserDetailsRepo;
import com.heavendeeds.heavendeeds.response.Response;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class HAVEENpropertyServiceImpl implements HAVEENpropertyService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HAVEENpropertyDetailsRepo haveeNpropertyDetailsRepo;

    @Autowired
    HAVEENuserDetailsRepo haveeNuserDetailsRepo;


    private static final Logger logger = LoggerFactory.getLogger(HAVEENpropertyServiceImpl.class);
    @Override
    public ResponseEntity<?> createProperty(HAVEENpropertyDetailsDto haveeNpropertyDetailsDto, MultipartFile file) throws HeavenApiException {
        Response<HAVEENpropertyDetailsDto> response = new Response<>();
        if(Objects.nonNull(haveeNpropertyDetailsDto)&&Objects.isNull(haveeNpropertyDetailsDto.getPropertyId())){
            try{
                HAVEENpropertyDetails haveeNpropertyDetails = objectMapper.convertValue(haveeNpropertyDetailsDto, HAVEENpropertyDetails.class);
                haveeNpropertyDetails.setPropertyCardPhoto(file.getBytes());
                Float prize=haveeNpropertyDetails.getPrize();
                String amount= convertPrizeToString(prize);
                haveeNpropertyDetails.setAmount(amount);
                haveeNpropertyDetailsRepo.save(haveeNpropertyDetails);
                HAVEENpropertyDetailsDto haveeNpropertyDetailsDto1=objectMapper.convertValue(haveeNpropertyDetails,HAVEENpropertyDetailsDto.class);
                haveeNpropertyDetailsDto1.setPropertyCardPhoto(null);
                logger.info("Property details saved successfully. {}", convertObjectToString(haveeNpropertyDetailsDto1));
                response.setMessage(Messages.PROPERTY_CREATED_SUCCESSFULLY.getMessages());
                response.setStatus(HttpStatus.OK.value());
                response.setData(haveeNpropertyDetailsDto1);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.info("Property id in this details",e.getMessage());
                throw new HeavenApiException(HeavenApiExceptionCode.PROPERTY_EXCEPTION,new Object[]{e.getMessage()});
            }
        }
        else {
            logger.info("Property id should not present in this details");
            throw new HeavenApiException(HeavenApiExceptionCode.PROPERTY_CREATE_EXCEPTION,null);
        }
    }


    @Override
    public ResponseEntity<?> updateProperty(HAVEENpropertyDetailsDto haveeNpropertyDetailsDto) throws HeavenApiException {
        Response<HAVEENpropertyDetailsDto> response = new Response<>();
        if ((Objects.nonNull(haveeNpropertyDetailsDto) && Objects.nonNull(haveeNpropertyDetailsDto.getUserId()) && Objects.isNull(haveeNpropertyDetailsDto.getPropertyCardPhoto()))) {
            HAVEENpropertyDetails haveeNpropertyDetails = objectMapper.convertValue(haveeNpropertyDetailsDto, HAVEENpropertyDetails.class);
            HAVEENpropertyDetails haveeNpropertyDetails1 = haveeNpropertyDetailsRepo.findByPropertyId(haveeNpropertyDetails.getPropertyId());
            if (Objects.isNull(haveeNpropertyDetails1)) {
                logger.error("Property details not found for the user");
                throw new HeavenApiException(HeavenApiExceptionCode.PROPERTY_EXCEPTION, null);
            }
            try {
                HAVEENpropertyDetails haveeNpropertyDetails2=haveeNpropertyDetailsRepo.save(haveeNpropertyDetails);
                HAVEENpropertyDetailsDto haveeNpropertyDetailsDto1 = objectMapper.convertValue(haveeNpropertyDetails2, HAVEENpropertyDetailsDto.class);
                logger.info("User Property details updated successfully: {}", convertObjectToString(haveeNpropertyDetailsDto1));
                response.setMessage(Messages.USER_PROPERTY_UPDATED.getMessages());
                response.setStatus(HttpStatus.OK.value());
                response.setData(haveeNpropertyDetailsDto1);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error when updating Property details for the user in try catch");
                throw new HeavenApiException(HeavenApiExceptionCode.INVALID_INPUT_EXCEPTION,new Object[]{e.getMessage()});
            }

        }
        logger.error("Error when updating Property details for the user");
        throw new HeavenApiException(HeavenApiExceptionCode.PROPERTY_EXCEPTION,null);
    }

    @Override
    public ResponseEntity<?> deleteProperty(Integer propertyId) throws HeavenApiException {
        Response<HAVEENuserDetailsDto> response = new Response<>();

        if (haveeNpropertyDetailsRepo.existsByPropertyId(propertyId)) {
            try {
                HAVEENpropertyDetails haveeNpropertyDetails = haveeNpropertyDetailsRepo.findByPropertyId(propertyId);
                haveeNpropertyDetailsRepo.delete(haveeNpropertyDetails);
                logger.info("Property is deleted successfully. {}", convertObjectToString(haveeNpropertyDetails));
                response.setData(null);
                response.setMessage(Messages.PROPERTY_DELETED.getMessages());
                response.setStatus(HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error when deleting the property {}", e.getMessage());
                throw new HeavenApiException(HeavenApiExceptionCode.CLEAN_USER_EXCEPTION, new Object[]{e.getMessage()});
            }
        }
        logger.error("property is not found.{}", propertyId);
        throw new HeavenApiException(HeavenApiExceptionCode.PROPERTY_EXCEPTION, null);
    }

    @Override
    public ResponseEntity<?> getAllProperty(Pageable pageable) throws HeavenApiException {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Page<HAVEENpropertyDetails> haveeNpropertyDetails = haveeNpropertyDetailsRepo.findAll(pageable);
            haveeNpropertyDetails.stream().forEach(property ->property.setPropertyCardPhoto(null));
            response.put("data", haveeNpropertyDetails.getContent());
            response.put("totalPages", haveeNpropertyDetails.getTotalPages());
            response.put("totalRecords", haveeNpropertyDetails.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error when getting all property details",e.getMessage());
            throw new HeavenApiException(HeavenApiExceptionCode.GET_ALL_PROPERTY_EXCEPTION,new Object[]{e.getMessage()});
        }
    }

    @Override
    public ResponseEntity<?> findByUserId(Integer userId) throws HeavenApiException {
        Response<HAVEENpropertyDetailsDto> response = new Response<>();
        HAVEENpropertyDetails haveeNpropertyDetails = haveeNpropertyDetailsRepo.findByUserId(userId);

        if (Objects.nonNull(haveeNpropertyDetails)) {
            try {
                response.setStatus(HttpStatus.OK.value());
                HAVEENpropertyDetailsDto haveeNpropertyDetailsDto = objectMapper.convertValue(haveeNpropertyDetails, HAVEENpropertyDetailsDto.class);
                haveeNpropertyDetailsDto.setPropertyCardPhoto(null);
                response.setData(haveeNpropertyDetailsDto);
                response.setMessage(Messages.PROPERTY_DETAILS_RETRIEVED.getMessages());
                return ResponseEntity.ok(response);
            } catch (IllegalArgumentException e) {
                logger.error("Error when retrieving user property {}.", e.getMessage());
                throw new HeavenApiException(HeavenApiExceptionCode.INVALID_INPUT_EXCEPTION, null);
            }
        }
        logger.error("User detail is not found for user {}.", userId);
        throw new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION, null);
    }

    @Override
    public ResponseEntity<String> findBPropertyId(Integer propertyId) throws HeavenApiException {

        HAVEENpropertyDetails haveeNpropertyDetails = haveeNpropertyDetailsRepo.findByPropertyId(propertyId);
        try {
            if (Objects.isNull(haveeNpropertyDetails)) {
                logger.error("User details not found for the user");
                throw new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION,null);
            }
            byte[]propertyPic=haveeNpropertyDetails.getPropertyCardPhoto();
            String base64Images = Base64.getEncoder().encodeToString(propertyPic);
            return ResponseEntity.status(HttpStatus.OK).body(base64Images);
        } catch (HeavenApiException e) {
            logger.error("Error when retrieving user property photo {}.", e.getMessage());
            throw new HeavenApiException(HeavenApiExceptionCode.INVALID_INPUT_EXCEPTION, null);
        }
    }
    @Override
    public ResponseEntity<List<String>> getPresentCity() throws HeavenApiException {
       List<String> cities= haveeNpropertyDetailsRepo.findAllUniqueCities();
        logger.info("All city's are retrieved");
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }

    @Override
    public ResponseEntity<List<String>> getPresentBHK() throws HeavenApiException {
        List<String> bhkList= haveeNpropertyDetailsRepo.findAllUniqueBHK();

        List<String> customOrder= HAVEENConstant.customOrder;

        List<String> finalBHKList = new ArrayList<>();
        for (String bhk : customOrder) {
            if (bhkList.contains(bhk)) {
                finalBHKList.add(bhk);
            }
        }
        for (String bhk : bhkList) {
            if (!finalBHKList.contains(bhk)) {
                finalBHKList.add(bhk);
            }
        }
        logger.info("All bhk are retrieved");
        return ResponseEntity.status(HttpStatus.OK).body(finalBHKList);
    }



    private String convertPrizeToString(Float prize) {
        if (prize == null) {
            return "0";
        }
        if (prize >= 1_00_00_000) {
            float croreValue = prize / 1_00_00_000;
            return (croreValue % 1 == 0) ? String.format("%.0f Cr", croreValue) : String.format("%.1f Cr", croreValue);
        }
        else if (prize >= 1_00_000) {
            float lakhValue = prize / 1_00_000;
            return (lakhValue % 1 == 0) ? String.format("%.0f L", lakhValue) : String.format("%.1f L", lakhValue);
        }
        else {
            return String.valueOf(prize.intValue());
        }
    }


    public ResponseEntity<?> updateExisting() throws HeavenApiException {
        List<Integer> propId = haveeNpropertyDetailsRepo.findAllPropertyId();
        for (Integer propIds : propId) {
            HAVEENpropertyDetails property = haveeNpropertyDetailsRepo.findByPropertyId(propIds);
            Float prize = property.getPrize();
            String amount = convertPrizeToString(prize);
            property.setAmount(amount);
            haveeNpropertyDetailsRepo.save(property);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }

    private byte[] compressImage(MultipartFile file) throws HeavenApiException, IOException {

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(0.7f);
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
