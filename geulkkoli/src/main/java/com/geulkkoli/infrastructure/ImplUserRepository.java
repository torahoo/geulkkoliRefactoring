package com.geulkkoli.infrastructure;

import com.geulkkoli.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;
@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ImplUserRepository implements UserRepository {
    private final EntityManager entityManager;
    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User findUser = entityManager.find(User.class, id);
        return Optional.of(findUser);
    }

    public Optional<User> findByUserId(String loginId) {

        return entityManager.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", loginId)
                .getResultList()
                .stream().findAny();
    }
}