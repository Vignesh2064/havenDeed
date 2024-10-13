package com.heavendeeds.heavendeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Table(name = "HAVEEEN_PROECT_PROPERTY_DETAILS")
@Data
@Entity
public class HAVEENprojectPropertyDetails {

    @Id
    @Column(name = "PROJECT_PROPERTY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectPropertyId;

    @Column(name = "FLOOR")
    private Integer floor;

    @Column(name = "FLAT_SIZE")
    private Float flatSize;

    @Column(name = "FLATS")
    private Integer flats;

    @Column(name = "BLOCKS")
    private String blocks;

    @Column(name = "PRIZE")
    private Float prize;

    @Column(name = "SQUARE_FEET")
    private Float squareFeet;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "PROPERTY_ID")
    private Integer propertyId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID",insertable = false,updatable = false)
    @JsonIgnore
    private HAVEENuserDetails HAVEENuserDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROPERTY_ID",insertable = false,updatable = false)
    @JsonIgnore
    private HAVEENprojectDetails haveeNprojectDetails;

    @Column(name = "CREATED_DT")
    @CreationTimestamp
    private LocalDate createdDt;

    @Column(name = "MODIFIED_DT")
    @UpdateTimestamp
    private LocalDate modifiedDt;
}
