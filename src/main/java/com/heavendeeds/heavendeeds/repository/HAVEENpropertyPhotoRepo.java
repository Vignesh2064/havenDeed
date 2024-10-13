package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.entities.HAVEENpropertyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HAVEENpropertyPhotoRepo extends JpaRepository<HAVEENpropertyPhoto,Integer> {

    List<HAVEENpropertyPhoto> findByPropertyId(Integer propertyId);

}
