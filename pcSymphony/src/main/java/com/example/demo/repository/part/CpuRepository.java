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
public interface CpuRepository extends JpaRepository<CpuEntity, Integer> {
    @Query("""
         SELECT b FROM CpuEntity b 
         WHERE b.name like %:name% 
         ORDER BY b.id DESC 
        """)
    List<CpuEntity> selectCpuList(@Param("name") String name);

    Page<CpuEntity> findByNameContainingOrManufacturerContaining(String name, String manufacturer, Pageable pageable);

}
