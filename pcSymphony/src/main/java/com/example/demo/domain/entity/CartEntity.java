
package com.example.demo.domain.entity;
import jakarta.persistence.*;
import com.example.demo.domain.entity.part.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// cart Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity {
    //작성된 카트 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    Integer cartId;

    // cpu 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_id",
            referencedColumnName = "cpu_id")
    CpuEntity cpu;

    // cpuCooler 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpucooler_id",
            referencedColumnName = "cpucooler_id")
    CpuCoolerEntity cpucooler;

    // motherboard 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_id",
            referencedColumnName = "motherboard_id")
    MotherboardEntity motherboard;

    // memory 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id",
            referencedColumnName = "memory_id")
    MemoryEntity memory;

    // storage 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id",
            referencedColumnName = "storage_id")
    StorageEntity storage;

    // videocard 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videocard_id",
            referencedColumnName = "videocard_id")
    VideoCardEntity videocard;

    // powersupply 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "powersupply_id",
            referencedColumnName = "powersupply_id")
    PowerSupplyEntity powersupply;

    // Cover 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id",
            referencedColumnName = "cover_id")
    CoverEntity cover;

    //작성자 정보 (외래키)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id")
    MemberEntity user;

    @Builder
    public CartEntity(MemberEntity user) {
        this.user = user;
    }
}