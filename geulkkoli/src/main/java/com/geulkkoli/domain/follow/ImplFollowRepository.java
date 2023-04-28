package com.geulkkoli.domain.follow;

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
public class ImplFollowRepository implements FollowRepository {

    private final EntityManager em;

    @Override
    public Follow save(Follow follow) {
        em.persist(follow);
        return follow;
    }

    @Override
    public Optional<Follow> findById (Long followId) {
        Follow follow = em.find(Follow.class, followId);
        return Optional.of(follow);
    }

    @Override
    public List<Follow> findAll() {
        return em.createQuery("select follow from Follow follow", Follow.class)
                .getResultList();
    }

    @Override
    public void delete(Long followId) {
        Follow findfollow = em.find(Follow.class, followId);
        findfollow.getUser().getFollow().remove(findfollow);
        em.remove(findfollow);
    }

    public void clear () {
        em.close();
    }
}
