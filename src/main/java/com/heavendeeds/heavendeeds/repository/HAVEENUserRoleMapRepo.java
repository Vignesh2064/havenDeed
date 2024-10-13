package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENuserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HAVEENUserRoleMapRepo extends JpaRepository<HAVEENuserRoleMap, Integer> {
    HAVEENuserRoleMap findByUserId(Integer userId);
}
