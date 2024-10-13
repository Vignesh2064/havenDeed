package com.heavendeeds.heavendeeds.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class HAVEENuserAuthenticationDetailsDto {

    private Integer authId;

    private String password;

    private String otp;

    private String resetCode;

    private Character isUpdated;

    private LocalDateTime createdDt;

    private LocalDateTime modifiedDt;
}
