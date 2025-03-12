package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.MotherboardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


@Repository
public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Integer> {
    Page<MotherboardEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

//    @Query("SELECT m FROM MotherboardEntity m WHERE m.motherboardSocketCpu = :socket")
//    List<MotherboardEntity> findCompatibleMotherboards(@Param("socket") String socket);
//
//    @Query("SELECT m FROM MotherboardEntity m WHERE m.motherboardMemoryType = :memoryType")
//    List<MotherboardEntity> findCompatibleMotherboardsByMemory(@Param("memoryType") String memoryType);
//
//    @Query("SELECT m FROM MotherboardEntity m WHERE m.motherboardFormFactor = :formFactor")
//    List<MotherboardEntity> findMotherboardsByFormFactor(@Param("formFactor") String formFactor);

//    // cpu 선택했을때, memory선택했을때, 둘다 선택했을때
//    @Query("SELECT m FROM MotherboardEntity m WHERE " +
//            "(:socket IS NULL OR m.motherboardSocketCpu = :socket) AND " +
//            "(:memoryType IS NULL OR m.motherboardMemoryType = :memoryType)")
//    List<MotherboardEntity> findCompatibleMotherboards(
//            @Param("socket") String socket,
//            @Param("memoryType") String memoryType
//    );
//
//    //커버 선택했을 때
//    @Query("SELECT m FROM MotherboardEntity m WHERE m.motherboardFormFactor = :formFactor")
//    List<MotherboardEntity> findCompatibleMotherboardsByCover(@Param("formFactor") String formFactor);

    @Query("SELECT m FROM MotherboardEntity m WHERE " +
            "(:socket IS NULL OR m.motherboardSocketCpu = :socket) AND " +
            "(:memoryType IS NULL OR m.motherboardMemoryType = :memoryType) AND " +
            "(:formFactor IS NULL OR m.motherboardFormFactor = :formFactor)")
    List<MotherboardEntity> findCompatibleMotherboards(
            @Param("socket") String socket,
            @Param("memoryType") String memoryType,
            @Param("formFactor") String formFactor
    );


}