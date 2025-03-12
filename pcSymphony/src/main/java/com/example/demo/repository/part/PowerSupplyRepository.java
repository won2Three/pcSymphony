package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.PowerSupplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupplyEntity, Integer> {
    Page<PowerSupplyEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

//    @Query("SELECT p FROM PowerSupplyEntity p WHERE p.powerSupplyType LIKE CONCAT('%', :formFactor, '%')")
//    List<PowerSupplyEntity> findCompatiblePowerSupplies(@Param("formFactor") String formFactor);

//    @Query("SELECT p FROM PowerSupplyEntity p WHERE p.powerSupplyType = :coverPowerSupply")
//    List<PowerSupplyEntity> findCompatiblePowerSupplies(@Param("coverPowerSupply") String coverPowerSupply);

//    @Query("SELECT p FROM PowerSupplyEntity p WHERE " +
//            "EXISTS (SELECT 1 FROM CoverEntity c " +
//            "WHERE c.coverPowerSupply LIKE CONCAT('%', p.powerSupplyType, '%'))")
//    List<PowerSupplyEntity> findCompatiblePowerSupplies(@Param("coverPowerSupply") String coverPowerSupply);

    @Query("SELECT p FROM PowerSupplyEntity p WHERE " +
            "(:coverPowerSupply IS NULL OR p.powerSupplyType LIKE CONCAT('%', :coverPowerSupply, '%'))")
    List<PowerSupplyEntity> findCompatiblePowerSupplies(@Param("coverPowerSupply") String coverPowerSupply);
}