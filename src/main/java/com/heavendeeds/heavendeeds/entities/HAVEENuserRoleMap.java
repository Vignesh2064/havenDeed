package com.heavendeeds.heavendeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HAVEEN_USER_ROLE_MAP")
public class HAVEENuserRoleMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "USER_ID")
    private Integer userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID",insertable = false,updatable = false)
    private HAVEENuserRoles HAVEENuserRoles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID",insertable = false,updatable = false)
    @JsonIgnore
    private HAVEENuserDetails haveeNuserDetails;
}
