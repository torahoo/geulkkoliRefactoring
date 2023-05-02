package com.geulkkoli.domain.hashtag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ImplHashTagsRepository implements HashTagsRepository {

    private final EntityManager em;

    @Override
    public HashTags save(HashTags hashTag) {
        em.persist(hashTag);
        return hashTag;
    }

    @Override
    public Optional<HashTags> findById(Long hashTagId) {
        HashTags findHashTag = em.find(HashTags.class, hashTagId);
        return Optional.of(findHashTag);
    }

    @Override
    public List<HashTags> findAll() {
        return em.createQuery("select h from HashTags h", HashTags.class)
                .getResultList();
    }

    @Override
    public void delete(Long hashTagId) {
        HashTags findHashTag = em.find(HashTags.class, hashTagId);
        findHashTag.getPost().getHashTags().remove(findHashTag);
        em.remove(findHashTag);
    }

    public void clear () {
        em.close();
    }
}
