package com.heavendeeds.heavendeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "HAVEEN_USER_AUTHENTICATION_DETAILS")
public class HAVEENuserAuthenticationDetails {

    @Id
    @Column(name = "AUTH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;

    @Column(name = "OTP",nullable = false)
    private String otp;

    @Column(name = "RESET_CODE")
    @JsonIgnore
    private String resetCode;

    @Column(name = "IS_UPDATED")
    private Character isUpdated;

    @Column(name="CREATED_DT",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDt;

    @Column(name="MODIFIED_DT",insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedDt;
}
