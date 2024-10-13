package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENuserAuthenticationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HAVEENuserAuthenticationDetailsRepo extends JpaRepository<HAVEENuserAuthenticationDetails,Integer> {
}
