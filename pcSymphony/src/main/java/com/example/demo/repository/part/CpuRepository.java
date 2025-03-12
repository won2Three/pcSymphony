package com.example.demo.repository.part;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.entity.part.CpuEntity;

@Repository
public interface CpuRepository extends JpaRepository<CpuEntity, Integer>, CpuRepositoryCustom  {
    Page<CpuEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);
//    @Query("""
//         SELECT b FROM CpuEntity b
//         WHERE b.name like %:name%
//         ORDER BY b.id DESC
//        """)
//    List<CpuEntity> selectCpuList(@Param("name") String name);
//
//    Page<CpuEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);
//
//    @Query("""
//         SELECT c FROM CpuEntity c
//         WHERE (:motherboardSocket IS NOT NULL AND c.cpuSocket = :motherboardSocket)
//    """
//    )
//    List<CpuEntity> findCompatibleCpusByMotherboard(@Param("motherboardSocket") String motherboardSocket);
//
//    @Query("""
//    SELECT c FROM CpuEntity c
//    WHERE
//    (:memoryType IS NOT NULL AND (
//        LOWER(c.name) LIKE CONCAT('%', LOWER(:memoryType), '%')
//        OR LOWER(c.name) LIKE CONCAT('%', LOWER(:alternativeType), '%')
//        OR LOWER(c.name) LIKE CONCAT('%', LOWER(:thirdType), '%')
//    ))
//    """)
//    List<CpuEntity> findCompatibleCpusByMemory(
//            @Param("memoryType") String memoryType,
//            @Param("alternativeType") String alternativeType,
//            @Param("thirdType") String thirdType
//    );
//
//    @Query("""
//    SELECT c FROM CpuEntity c
//    WHERE
//    (:motherboardSocket IS NOT NULL AND c.cpuSocket = :motherboardSocket)
//    AND
//    (:memoryType IS NOT NULL AND (
//        LOWER(c.name) LIKE CONCAT('%', LOWER(:memoryType), '%')
//        OR LOWER(c.name) LIKE CONCAT('%', LOWER(:alternativeType), '%')
//        OR LOWER(c.name) LIKE CONCAT('%', LOWER(:thirdType), '%')
//    ))
//    """)
//    List<CpuEntity> findCompatibleCpusByMotherboardAndMemory(
//            @Param("motherboardSocket") String motherboardSocket,
//            @Param("memoryType") String memoryType,
//            @Param("alternativeType") String alternativeType,
//            @Param("thirdType") String thirdType
//    );



}