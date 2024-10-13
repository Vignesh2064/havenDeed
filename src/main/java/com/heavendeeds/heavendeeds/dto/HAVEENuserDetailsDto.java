package com.heavendeeds.heavendeeds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heavendeeds.heavendeeds.entities.HAVEENuserAuthenticationDetails;
import lombok.Data;

import java.time.LocalDate;
@Data
public class HAVEENuserDetailsDto {

    private Integer userId;

    private String name;

    private String email;

    private long phoneNum;

    private byte[] profilePic;

    @JsonIgnore
    private String token;

    private HAVEENuserAuthenticationDetails haveeNuserAuthenticationDetails;

    @JsonIgnore
    private String role;

    private String location;

    private String propertiesPosted;

    private Character verified;

    private LocalDate createdDt;

    private LocalDate modifiedDt;
}
