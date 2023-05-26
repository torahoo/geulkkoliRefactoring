package com.geulkkoli.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialInfoRepository extends JpaRepository<SocialInfo,Long> ,SocialInfoRepositoryCustom {
    Optional<SocialInfo> findSocialInfoBySocialTypeAndSocialType(String socialType, String socialId);

    Boolean existsBySocialTypeAndSocialId(String socialType, String socialId);
}
