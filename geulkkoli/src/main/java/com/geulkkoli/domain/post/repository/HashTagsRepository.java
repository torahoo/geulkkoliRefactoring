package com.geulkkoli.domain.post.repository;

import com.geulkkoli.domain.post.entity.HashTags;

import java.util.List;
import java.util.Optional;

public interface HashTagsRepository {
    HashTags save (HashTags hashTag);
    Optional<HashTags> findById (Long hashTagId);
    List<HashTags> findAll ();
    void delete (Long hashTagId);
}
