package com.geulkkoli.domain.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    HashTag findHashTagByHashTagName(String tagName);

    HashTag findHashTagByHashTagId(Long hashTagId);
}
