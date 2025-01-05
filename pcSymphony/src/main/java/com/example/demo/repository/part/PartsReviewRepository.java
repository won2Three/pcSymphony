package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.PartsReviewEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartsReviewRepository extends JpaRepository<PartsReviewEntity, Integer> {
    List<PartsReviewEntity> findByCpu_Id(int cpuId);

    List<PartsReviewEntity> findByCpucooler_Id(int cpucoolerId);

    List<PartsReviewEntity> findByMemory_Id(int memoryId);

    List<PartsReviewEntity> findByMotherboard_Id(int motherboardId);

    List<PartsReviewEntity> findByPowersupply_Id(int powersupplyId);

    List<PartsReviewEntity> findByStorage_Id(int storageId);

    List<PartsReviewEntity> findByVideocard_Id(int videocardId);

    List<PartsReviewEntity> findByCover_Id(int coverId);

    // 여러 부품을 조건으로 조회하는 메서드
    @Query("SELECT r FROM PartsReviewEntity r " +
            "WHERE (:cpuId IS NULL OR r.cpu.id = :cpuId) " +
            "AND (:cpucoolerId IS NULL OR r.cpucooler.id = :cpucoolerId) " +
            "AND (:memoryId IS NULL OR r.memory.id = :memoryId) " +
            "AND (:motherboardId IS NULL OR r.motherboard.id = :motherboardId) " +
            "AND (:storageId IS NULL OR r.storage.id = :storageId) " +
            "AND (:videocardId IS NULL OR r.videocard.id = :videocardId) " +
            "AND (:powersupplyId IS NULL OR r.powersupply.id = :powersupplyId) " +
            "AND (:coverId IS NULL OR r.cover.id = :coverId) ")
    List<PartsReviewEntity> findByParts(
            @Param("cpuId") Integer cpuId,
            @Param("cpucoolerId") Integer cpucoolerId,
            @Param("memoryId") Integer memoryId,
            @Param("motherboardId") Integer motherboardId,
            @Param("storageId") Integer storageId,
            @Param("videocardId") Integer videocardId,
            @Param("powersupplyId") Integer powersupplyId,
            @Param("coverId") Integer coverId);
}
