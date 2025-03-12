package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CoverEntity;
import com.example.demo.domain.entity.part.CpuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CoverRepository extends JpaRepository<CoverEntity, Integer> {
    Page<CoverEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

//    @Query("SELECT c FROM CoverEntity c WHERE c.coverMotherboardFormFactor = :formFactor")
//    List<CoverEntity> findCompatibleCovers(@Param("formFactor") String formFactor);
//
//    @Query("SELECT c FROM CoverEntity c WHERE c.coverMaxVideoCardLength >= :videoCardLength")
//    List<CoverEntity> findCompatibleCoversForVideoCard(@Param("videoCardLength") Double videoCardLength);
//
//    @Query("SELECT c FROM CoverEntity c WHERE c.coverPowerSupply = :powerSupplyType")
//    List<CoverEntity> findCompatibleCoversForPowerSupply(@Param("powerSupplyType") String powerSupplyType);

    // ✅ 마더보드 폼팩터와 호환되는 케이스 찾기
    @Query("SELECT c FROM CoverEntity c WHERE c.coverMotherboardFormFactor = :formFactor")
    List<CoverEntity> findCoversByMotherboard(@Param("formFactor") String formFactor);

    // ✅ 비디오카드 길이를 수용할 수 있는 케이스 찾기
    @Query("SELECT c FROM CoverEntity c WHERE c.coverMaxVideoCardLength >= :videoCardLength")
    List<CoverEntity> findCoversByVideoCard(@Param("videoCardLength") Double videoCardLength);

    // ✅ 파워서플라이와 호환되는 케이스 찾기
    @Query("SELECT c FROM CoverEntity c WHERE c.coverPowerSupply LIKE CONCAT('%', :powerSupplyType, '%')")
    List<CoverEntity> findCoversByPowerSupply(@Param("powerSupplyType") String powerSupplyType);
}