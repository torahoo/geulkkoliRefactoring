package com.geulkkoli.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SocialInfoRepository extends JpaRepository<SocialInfo,Long> ,SocialInfoRepositoryCustom {
    Optional<SocialInfo> findSocialInfoBySocialTypeAndSocialType(String socialType, String socialId);

    Boolean existsBySocialTypeAndSocialId(String socialType, String socialId);
}
