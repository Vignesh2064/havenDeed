package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENuserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HAVEENuserRolesRepo extends JpaRepository<HAVEENuserRoles,Integer> {
}
