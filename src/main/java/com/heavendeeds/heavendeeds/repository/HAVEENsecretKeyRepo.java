package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENsecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface HAVEENsecretKeyRepo extends JpaRepository<HAVEENsecretKey,Integer> {
    HAVEENsecretKey findBySecretId(Integer secretId);
}
