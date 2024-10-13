package com.heavendeeds.heavendeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "HAVEEN_PROJECT_DETAILS")
public class HAVEENprojectDetails {

    @Id
    @Column(name = "PROPERTY_PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column(name = "HOW_ARE_YOU")
    private String howAreYou;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PROPERTY_NAME")
    private String propertyName;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "PROJECT_CARD_PHOTO")
    private byte[] projectCardPhoto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID",insertable = false,updatable = false)
    @JsonIgnore
    private HAVEENuserDetails HAVEENuserDetails;

    @Column(name = "CREATED_DT")
    @CreationTimestamp
    private LocalDate createdDt;


    @Column(name = "MODIFIED_DT")
    @UpdateTimestamp
    private LocalDate modifiedDt;
}
