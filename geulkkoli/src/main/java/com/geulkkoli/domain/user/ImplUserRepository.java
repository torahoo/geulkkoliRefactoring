package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.JoinForm;
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

    @Override
    public Optional<User> findByEmail(String email) {
        return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public Optional<User> findByNickName(String nickName) {
        return entityManager.createQuery("select u from User u where u.nickName = :nickName", User.class)
                .setParameter("nickName", nickName)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public Optional<User> findByPhoneNo(String phoneNo) {
        return entityManager.createQuery("select u from User u where u.phoneNo= :phoneNo", User.class)
                .setParameter("phoneNo", phoneNo)
                .getResultList()
                .stream().findAny();
    }

}