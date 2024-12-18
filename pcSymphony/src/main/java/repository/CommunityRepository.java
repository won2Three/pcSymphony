package repository;

import domain.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {
    public List<CommunityEntity> selectAll();
}
