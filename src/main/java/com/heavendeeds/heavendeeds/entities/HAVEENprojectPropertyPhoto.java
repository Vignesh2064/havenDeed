package com.heavendeeds.heavendeeds.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "HAVEEN_PROJECT_PROPERTY_PHOTO")
public class HAVEENprojectPropertyPhoto {
    @Id
    @Column(name = "PROJECT_PROPERTY_PHO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyPhotoId;

    @Column(name = "PROPERTY_ID")
    private Integer propertyId;

    @Lob
    @Column(name = "PROPERTY_PHOTO")
    private byte[] propertyPhoto;

    @Column(name = "CREATED_DT")
    @CreationTimestamp
    private LocalDate createdDt;


    @Column(name = "MODIFIED_DT")
    @UpdateTimestamp
    private LocalDate modifiedDt;
}
