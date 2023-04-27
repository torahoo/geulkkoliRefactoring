package com.geulkkoli.domain.hashtag;

import java.util.List;
import java.util.Optional;

public interface HashTagsRepository {
    HashTags save (HashTags hashTag);
    Optional<HashTags> findById (Long hashTagId);
    List<HashTags> findAll ();
    void delete (Long hashTagId);
}
