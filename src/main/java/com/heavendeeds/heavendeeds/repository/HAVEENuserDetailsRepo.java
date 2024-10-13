package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface HAVEENuserDetailsRepo extends JpaRepository<HAVEENuserDetails, Integer> {

    //FORMuserRegisterDetails findByEmail(String email);

    Optional<HAVEENuserDetails> findByEmail(String email);

    @Query("SELECT u FROM HAVEENuserDetails u WHERE u.email = :email")
    HAVEENuserDetails findOneByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    HAVEENuserDetails findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);

    HAVEENuserDetails findByUserIdOrderByCreatedDtDesc(Integer userId);
}
