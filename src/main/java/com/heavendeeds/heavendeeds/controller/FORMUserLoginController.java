package com.heavendeeds.heavendeeds.controller;

import com.heavendeeds.heavendeeds.dto.HAVEENuserDetailsDto;
import com.heavendeeds.heavendeeds.response.Response;
import com.heavendeeds.heavendeeds.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway")
public class FORMUserLoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Response<HAVEENuserDetailsDto>> login(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<Response<HAVEENuserDetailsDto>>  responseResponseEntity = loginService.login(request);
        return ResponseEntity.status(responseResponseEntity.getStatusCode()).body(responseResponseEntity.getBody());
    }
}
