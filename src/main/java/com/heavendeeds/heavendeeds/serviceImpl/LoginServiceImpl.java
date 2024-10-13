package com.heavendeeds.heavendeeds.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import com.heavendeeds.heavendeeds.entities.HAVEENuserRoleMap;
import com.heavendeeds.heavendeeds.response.Response;
import com.heavendeeds.heavendeeds.service.LoginService;
import com.heavendeeds.heavendeeds.constraint.CommonConstraint;
import com.heavendeeds.heavendeeds.repository.HAVEENuserDetailsRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENUserRoleMapRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.security.sasl.AuthenticationException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    HAVEENuserDetailsRepo HAVEENuserDetailsRepo;
    @Autowired
    HAVEENUserRoleMapRepo HAVEENUserRoleMapRepo;
    @Override
    public ResponseEntity<Response<HAVEENuserDetailsDto>> login(HttpServletRequest request) {

        Response<HAVEENuserDetailsDto> response = new Response<>();
        try {
            String basicAuth = parseJwt(request);
            if (basicAuth != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(basicAuth);
                String decodedString = new String(decodedBytes);
                List<String> authDetails = Arrays.stream(decodedString.split(":")).toList();
                logger.info("User {} initiated login.",authDetails.get(0));
                Optional<HAVEENuserDetails> user = HAVEENuserDetailsRepo.findByEmail(authDetails.get(0));
                if(user.isEmpty()) {
                    logger.info("User {} does not exists.",authDetails.get(0));
                    throw new AuthenticationException("User does not exists.");
                }
                HAVEENuserDetailsDto userDetails = objectMapper.convertValue(user.get(), HAVEENuserDetailsDto.class);
                HAVEENuserRoleMap haveeNuserRoleMap = HAVEENUserRoleMapRepo.findByUserId(userDetails.getUserId());
                userDetails.setRole(haveeNuserRoleMap.getHAVEENuserRoles().getRole());
                response.setData(userDetails);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage(CommonConstraint.LOGIN_SUCCESS);
            }
        } catch (AuthenticationException e) {
            logger.error("User has given invalid login details.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage(e.getMessage());
        } catch (Exception e)
        {
            logger.error("Error when trying to login.{}",e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth)) {
            return headerAuth;
        }
        logger.error("Authorization in header is not available.");
        return null;
    }
}
