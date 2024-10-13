package com.heavendeeds.heavendeeds.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HAVEEN_SECRET_DETAILS")
public class HAVEENsecretKey {

    @Id
    @Column(name = "SECRET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer secretId;

    @Column(name = "SECRET_KEY")
    private String secretKey;

    @Column(name = "API_KEY")
    private String apiKey;

    @Column(name ="ORG_KEY")
    private String orgKey;
}
