package com.heavendeeds.heavendeeds.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HAVEEEN_USER_ROLES")
public class HAVEENuserRoles {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "role")
    private String role;
}
