package com.heavendeeds.heavendeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "HAVEEN_PROPERTY_PHOTO")
public class HAVEENpropertyPhoto {

    @Id
    @Column(name = "PROPERTY_PHO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyPhotoId;

    @Column(name = "PROPERTY_ID")
    private Integer propertyId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROPERTY_ID",insertable = false,updatable = false)
    @JsonIgnore
    private HAVEENpropertyDetails haveeNpropertyDetails;

    @Column(name = "PROPERTY_PHOTO")
    private byte[] propertyPhoto;

    @Column(name = "CREATED_DT")
    @CreationTimestamp
    private LocalDate createdDt;


    @Column(name = "MODIFIED_DT")
    @UpdateTimestamp
    private LocalDate modifiedDt;
}
