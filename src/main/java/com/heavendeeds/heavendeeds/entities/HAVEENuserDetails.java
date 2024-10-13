package com.heavendeeds.heavendeeds.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "HAVEEN_USER_DETAILS")
public class HAVEENuserDetails {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL",unique = true,nullable = false)
    private String email;

    @Column(name = "PHONE_NUM")
    private long phoneNum;

    @Lob
    @Column(name = "PROFILE_PIC")
    private byte[] profilePic;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "AUTH_ID")
    private HAVEENuserAuthenticationDetails haveeNuserAuthenticationDetails;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PROPERTIES_POSTED")
    private String propertiesPosted;

    @Column(name = "VERIFIED")
    private Character verified;

    @Column(name = "CREATED_DT")
    @CreationTimestamp
    private LocalDate createdDt;

    @Column(name = "MODIFIED_DT")
    @UpdateTimestamp
    private LocalDate modifiedDt;
}