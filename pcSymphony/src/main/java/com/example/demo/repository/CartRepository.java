package com.example.demo.repository;
import com.example.demo.domain.entity.MemberEntity;
import com.example.demo.repository.part.*;

import com.example.demo.domain.entity.CartEntity;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    CartEntity findByUser_MemberId(String memberId);
    Optional<CartEntity> findByUser(MemberEntity user);
    // 각 항목을 null로 설정하는 메서드
    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.cpu = NULL WHERE c.cpu IS NOT NULL")
    void removeCpu();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.cpucooler = NULL WHERE c.cpucooler IS NOT NULL")
    void removeCpucooler();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.videocard = NULL WHERE c.videocard IS NOT NULL")
    void removeVideocard();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.memory = NULL WHERE c.memory IS NOT NULL")
    void removeMemory();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.storage = NULL WHERE c.storage IS NOT NULL")
    void removeStorage();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.motherboard = NULL WHERE c.motherboard IS NOT NULL")
    void removeMotherboard();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.powersupply = NULL WHERE c.powersupply IS NOT NULL")
    void removePowersupply();

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c SET c.cover = NULL WHERE c.cover IS NOT NULL")
    void removeCover();
}

//        //가져올 정보 - userid, cpu, cpucooler, motherboard, memory, videocard, storage, powersupply, cover
//    List<CartEntity> selectCartList(
//            @Param("user_id")String userid,
//            @Param("cpu") String cpu,
//            @Param("cpuCooler") String cpuCooler,
//            @Param("motherboard") String motherboard,
//            @Param("memory") String memory,
//            @Param("videoCard") String videoCard,
//            @Param("storage") String storage,
//            @Param("powerSupply") String powerSupply,
//            @Param("cover") String cover
//    );
