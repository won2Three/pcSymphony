package service;

import domain.entity.CommunityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.CommunityRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    public List<CommunityEntity> CommunityList() {
        return communityRepository.selectAll();
    }

}
