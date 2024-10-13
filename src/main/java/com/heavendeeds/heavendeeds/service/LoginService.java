package com.heavendeeds.heavendeeds.service;

import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    public ResponseEntity<Response<HAVEENuserDetailsDto>> login(HttpServletRequest request);
}
