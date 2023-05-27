package com.geulkkoli.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialInfoRepository extends JpaRepository<SocialInfo, Long>, SocialInfoRepositoryCustom {
    Optional<SocialInfo> findSocialInfoBySocialTypeAndSocialId(String socialType, String socialId);
    Optional<SocialInfo> findSocialInfoBySocialTypeAndAndUser_Email(String socialType, String email);

    Boolean existsBySocialTypeAndSocialId(String socialType, String socialId);

    Boolean existsSocialInfoBySocialTypeAndAndUser_UserId(String socialType, Long userId);

    Boolean existsSocialInfoBySocialTypeAndAndUser_Email(String socialType, String email);

    List<SocialInfo> findAllByUser_Email(String email);
}
