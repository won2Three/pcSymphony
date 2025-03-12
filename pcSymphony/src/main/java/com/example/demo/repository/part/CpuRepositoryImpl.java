package com.example.demo.repository.part;

import com.example.demo.domain.entity.part.CpuEntity;
import com.example.demo.domain.entity.part.QCpuEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CpuRepositoryImpl implements CpuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired  // ✅ 빈 주입 방식 수정
    public CpuRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CpuEntity> findCompatibleCpusByMotherboard(String motherboardSocket) {
        QCpuEntity cpu = QCpuEntity.cpuEntity;

        return queryFactory
                .selectFrom(cpu)
                .where(cpu.cpuSocket.eq(motherboardSocket))
                .fetch();
    }

    @Override
    public List<CpuEntity> findCompatibleCpusByMemory(String memoryType, String alternativeType, String thirdType) {
        QCpuEntity cpu = QCpuEntity.cpuEntity;

        return queryFactory
                .selectFrom(cpu)
                .where(
                        cpu.name.containsIgnoreCase(memoryType)
                                .or(cpu.name.containsIgnoreCase(alternativeType))
                                .or(cpu.name.containsIgnoreCase(thirdType))
                )
                .fetch();
    }

    @Override
    public List<CpuEntity> findCompatibleCpusByMotherboardAndMemory(String motherboardSocket, String memoryType, String alternativeType, String thirdType) {
        QCpuEntity cpu = QCpuEntity.cpuEntity;

        return queryFactory
                .selectFrom(cpu)
                .where(
                        cpu.cpuSocket.eq(motherboardSocket),
                        cpu.name.containsIgnoreCase(memoryType)
                                .or(cpu.name.containsIgnoreCase(alternativeType))
                                .or(cpu.name.containsIgnoreCase(thirdType))
                )
                .fetch();
    }
}

//    private String getAlternativeMemoryType(String memoryType) {
//        return switch (memoryType) {
//            case "DDR4" -> "DDR5";
//            case "DDR5" -> "DDR4";
//            case "DDR3" -> "DDR4";
//            default -> "";
//        };
//    }