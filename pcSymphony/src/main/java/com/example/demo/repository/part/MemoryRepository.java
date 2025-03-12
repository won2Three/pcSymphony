package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.MemoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Integer> {


    Page<MemoryEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

    // ✅ CPU와 마더보드의 메모리 폼팩터를 기준으로 호환 메모리 조회
    @Query("""
    SELECT m FROM MemoryEntity m 
    WHERE 
    (:cpuMemoryType IS NOT NULL AND m.memoryFormFactor LIKE CONCAT(:cpuMemoryType, '%'))
    OR 
    (:motherboardMemoryType IS NOT NULL AND m.memoryFormFactor LIKE CONCAT(:motherboardMemoryType, '%'))
""")
    List<MemoryEntity> findCompatibleMemories(
            @Param("cpuMemoryType") String cpuMemoryType,
            @Param("motherboardMemoryType") String motherboardMemoryType
    );



    // ✅ CPU와 마더보드의 공통 메모리 폼팩터를 LIKE 검색으로 조회 (기존 IN 방식 개선)
    @Query("""
        SELECT m FROM MemoryEntity m 
        WHERE 
        (:cpuMemoryType IS NOT NULL AND m.memoryFormFactor LIKE CONCAT(:cpuMemoryType, '%'))
        OR
        (:motherboardMemoryType IS NOT NULL AND m.memoryFormFactor LIKE CONCAT(:motherboardMemoryType, '%'))
    """)
    List<MemoryEntity> findCompatibleMemoriesByCpuOrMotherboard(
            @Param("cpuMemoryType") String cpuMemoryType,
            @Param("motherboardMemoryType") String motherboardMemoryType
    );

    // ✅ 특정 폼팩터를 가진 메모리 조회 (LIKE 검색)
    @Query("SELECT m FROM MemoryEntity m WHERE m.memoryFormFactor LIKE CONCAT(:formFactor, '%')")
    List<MemoryEntity> findByMemoryFormFactorLike(@Param("formFactor") String formFactor);
}