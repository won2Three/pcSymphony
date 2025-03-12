package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.StorageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
    Page<StorageEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

    @Query("SELECT s FROM StorageEntity s")
    List<StorageEntity> findAllStorages(); // ✅ 모든 저장장치 조회

}