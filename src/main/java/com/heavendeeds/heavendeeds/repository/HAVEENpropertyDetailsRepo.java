package com.heavendeeds.heavendeeds.repository;

import com.heavendeeds.heavendeeds.entities.HAVEENpropertyDetails;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HAVEENpropertyDetailsRepo extends JpaRepository<HAVEENpropertyDetails,Integer> {
    HAVEENpropertyDetails findByUserId(Integer userId);
    HAVEENpropertyDetails findByPropertyId(Integer propertyId);
    boolean existsByPropertyId(Integer propertyId);

//    List<HAVEENpropertyDetails>findByCategoryAndCity(Pageable pageable,String category, String city);

    @Query("SELECT p FROM HAVEENpropertyDetails p WHERE p.propertyType = :propertyType AND p.city = :city")
    List<HAVEENpropertyDetails> findByCategoryAndCity(@Param("propertyType") String propertyType, @Param("city") String city,Pageable pageable);
//    @Query("SELECT p FROM HAVEENpropertyDetails p WHERE p.city = :city " +
//            "AND (:landMark IS NULL OR p.landMark LIKE CONCAT('%', :landMark, '%')) " +
//            "AND (:bhk IS NULL OR p.bhk IN ('1RK','1BHK','1.5BHK', '2BHK', '3BHK', '3.5BHK', '2.5BHK', '4BHK') OR p.bhk = :bhk)")
//    List<HAVEENpropertyDetails> searchProperty(@Param("city") String city, @Param("landMark") String landMark,
//                                               @Param("bhk") String bhk, Pageable pageable);
//

        @Query("SELECT p FROM HAVEENpropertyDetails p WHERE p.city = :city " +
                "AND (:landMark IS NULL OR p.landMark LIKE CONCAT('%', :landMark, '%')) " +
                "AND (:bhk IS NULL OR p.bhk = :bhk)")
        List<HAVEENpropertyDetails> searchProperty(@Param("city") String city,
                                                   @Param("landMark") String landMark,
                                                   @Param("bhk") String bhk,
                                                   Pageable pageable);

    @Query("SELECT p FROM HAVEENpropertyDetails p WHERE p.city = :city " +
            "AND (:landMark IS NULL OR p.landMark LIKE CONCAT('%', :landMark, '%'))")
    List<HAVEENpropertyDetails> searchPropertyIgnoreBhk(@Param("city") String city,
                                                        @Param("landMark") String landMark,
                                                        Pageable pageable);

    @Query("SELECT p.propertyType, COUNT(p) FROM HAVEENpropertyDetails p GROUP BY p.propertyType")
    List<Object[]> countPropertiesByType();

    @Query("SELECT DISTINCT p.city FROM HAVEENpropertyDetails p")
    List<String> findAllUniqueCities();

    @Query("SELECT DISTINCT p.bhk FROM HAVEENpropertyDetails p")
    List<String> findAllUniqueBHK();
    @Query("SELECT DISTINCT p.propertyId FROM HAVEENpropertyDetails p")
    List<Integer> findAllPropertyId();
//    @Query("SELECT p FROM HAVEENpropertyDetails p WHERE p.city = :city " +
//            "AND (:propertyTypes IS NULL OR p.propertyType IN :propertyTypes) " +
//            "AND (:bhkTypes IS NULL OR p.bhk IN :bhkTypes) " +
//            "AND (:propertyStatus IS NULL OR p.propertyStatus = :propertyStatus) " +
//            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.prize BETWEEN :minPrice AND :maxPrice) " +
//            "AND (:minSquareFeet IS NULL OR :maxSquareFeet IS NULL OR p.squareFeet BETWEEN :minSquareFeet AND :maxSquareFeet)")
//    Page<HAVEENpropertyDetails> filterProperty(
//            @Param("city") String city,
//            @Param("propertyTypes") List<String> propertyTypes,
//            @Param("bhkTypes") List<String> bhkTypes,
//            @Param("propertyStatus") String propertyStatus,
//            @Param("minPrice") double minPrice,
//            @Param("maxPrice") double maxPrice,
//            @Param("minSquareFeet") double minSquareFeet,
//            @Param("maxSquareFeet") double maxSquareFeet,
//            Pageable pageable);
//@Query("SELECT p FROM HAVEENpropertyDetails p WHERE p.city = :city " +
//        "AND (:propertyTypes IS NULL OR p.propertyType IN :propertyTypes) " +
//        "AND (:bhkTypes IS NULL OR p.bhk IN :bhkTypes) " +
//        "AND (:propertyStatus IS NULL OR p.propertyStatus = :propertyStatus) " +
//        "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.prize BETWEEN :minPrice AND :maxPrice) " +
//        "AND (:minSquareFeet IS NULL OR :maxSquareFeet IS NULL OR p.squareFeet BETWEEN :minSquareFeet AND :maxSquareFeet)")
//Page<HAVEENpropertyDetails> filterProperty(
//        @Param("city") String city,
//        @Param("propertyTypes") List<String> propertyTypes,
//        @Param("bhkTypes") List<String> bhkTypes,
//        @Param("propertyStatus") String propertyStatus,
//        @Param("minPrice") Float minPrice,
//        @Param("maxPrice") Float maxPrice,
//        @Param("minSquareFeet") Float minSquareFeet,
//        @Param("maxSquareFeet") Float maxSquareFeet,
//        Pageable pageable);


    @Query("SELECT p FROM HAVEENpropertyDetails p WHERE(:city IS NULL OR p.city = :city) " +
            "AND (:propertyTypes IS NULL OR p.propertyType IN :propertyTypes) " +
            "AND (:bhkTypes IS NULL OR p.bhk IN :bhkTypes) " +
            "AND (:propertyStatus IS NULL OR p.propertyStatus IN :propertyStatus) " +
            "AND (:minPrice IS NULL OR p.prize >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.prize <= :maxPrice) " +
            "AND (:minSquareFeet IS NULL OR p.squareFeet >= :minSquareFeet) " +
            "AND (:maxSquareFeet IS NULL OR p.squareFeet <= :maxSquareFeet)")
    Page<HAVEENpropertyDetails> filterProperty(
            @Param("city") String city,
            @Param("propertyTypes") List<String> propertyTypes,
            @Param("bhkTypes") List<String> bhkTypes,
            @Param("propertyStatus") List<String> propertyStatus,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("minSquareFeet") Float minSquareFeet,
            @Param("maxSquareFeet") Float maxSquareFeet,
            Pageable pageable);



}