package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.PartsReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
