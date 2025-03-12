package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.VideoCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoCardRepository extends JpaRepository<VideoCardEntity, Integer> {
    Page<VideoCardEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

    @Query("SELECT v FROM VideoCardEntity v WHERE v.videoCardLength <= :maxLength")
    List<VideoCardEntity> findCompatibleVideoCards(@Param("maxLength") Integer maxLength);

}