package com.heavendeeds.heavendeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "HAVEEN_PROPERTY_DETAILS")
public class HAVEENpropertyDetails {

    @Id
    @Column(name = "PROPERTY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyId;

    @Column(name = "BHK")
    private String bhk;

    @Column(name = "PROPERTY_STATUS")
    private String propertyStatus;

    @Column(name = "PROPERTY_NAME")
    private String propertyName;

    @Column(name = "PROPERTY_TYPE")
    private String propertyType;

    @Column(name = "PRIZE")
    private Float prize;

    @Column(name = "SQUARE_FEET")
    private Float squareFeet;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "LANDMARK")
    private String landMark;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AMOUNT")
    private String amount;

    @Lob
    @Column(name = "PROPERTY_CARD_PHOTO")
    private byte[] propertyCardPhoto;

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

    @Column(name = "PROPERTY")
    private String property;

    @Column(name = "PER_SQUARE_FEET")
    private Float perSquareFeet;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;
}
