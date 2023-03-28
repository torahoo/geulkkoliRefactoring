package com.geulkkoli.respository;

import com.geulkkoli.domain.Users;
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
public class ImplUsersRepository implements UsersRepository {

    private final EntityManager entityManager;
    @Override
    public Users save(Users user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<Users> findById(Long id) {
        Users findUser = entityManager.find(Users.class, id);
        return Optional.of(findUser);
    }
}
