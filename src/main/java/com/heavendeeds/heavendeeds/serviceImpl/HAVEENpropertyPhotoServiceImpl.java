package com.heavendeeds.heavendeeds.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavendeeds.heavendeeds.constant.Messages;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyPhoto;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.exception.HeavenApiExceptionCode;
import com.heavendeeds.heavendeeds.repository.HAVEENpropertyDetailsRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENpropertyPhotoRepo;
import com.heavendeeds.heavendeeds.response.Response;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HAVEENpropertyPhotoServiceImpl implements HAVEENpropertyPhotoService {

    @Autowired
    HAVEENpropertyPhotoRepo haveeNpropertyPhotoRepo;

    ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(HAVEENpropertyPhotoServiceImpl.class);
    @Override
    public ResponseEntity<?> uploadPropertyPhoto(Integer propertyId, MultipartFile[] file) throws HeavenApiException {
        List<HAVEENpropertyPhoto> photoList = new ArrayList<>();
        HAVEENpropertyPhoto haveeNpropertyPhoto = new HAVEENpropertyPhoto();
        for (MultipartFile files : file) {
            if (!files.isEmpty()) {
                try {
                    haveeNpropertyPhoto.setPropertyId(propertyId);
                    haveeNpropertyPhoto.setPropertyPhoto(files.getBytes());
                    photoList.add(haveeNpropertyPhoto);
                } catch (IOException e) {
                    logger.error("Error when uploading property photos");
                    throw new HeavenApiException(HeavenApiExceptionCode.USER_ROLE_NOT_FOUND, new Object[]{e.getMessage()});
                }
            }
        }
        List<HAVEENpropertyPhoto>haveeNpropertyPhotos=haveeNpropertyPhotoRepo.saveAll(photoList);
        haveeNpropertyPhotos.stream().map(haveeNpropertyPhoto1 ->{haveeNpropertyPhoto1.setPropertyPhoto(null);
            return haveeNpropertyPhoto1;
        }).collect(Collectors.toList());
        logger.info("User property photos saved");
        return ResponseEntity.status(HttpStatus.OK).body(responseBuilder(HttpStatus.OK.value(), haveeNpropertyPhotos, Messages.PROPERTY_DETAILS_RETRIEVED.getMessages()));
    }

    @Override
    public List<byte[]> findByPropertyId(Integer propertyId) throws HeavenApiException {
                 List<HAVEENpropertyPhoto> haveeNpropertyPhotos=haveeNpropertyPhotoRepo.findByPropertyId(propertyId);

        if (Objects.isNull(haveeNpropertyPhotos)){
            logger.error("User details not found for the user");
            throw new HeavenApiException(HeavenApiExceptionCode.USER_NOT_FOUND_EXCEPTION, null);
        }
        return haveeNpropertyPhotos.stream()
                .map(HAVEENpropertyPhoto::getPropertyPhoto)
                .collect(Collectors.toList());
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
