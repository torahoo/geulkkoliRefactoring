package com.geulkkoli.respository;

import com.geulkkoli.domain.user.Users;
import com.geulkkoli.domain.user.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void save() {
        Users user = Users.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        Users saveUser = usersRepository.save(user);

        assertThat(user).isEqualTo(user);
    }

    @Test
    void findById() {
        Users user = Users.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        usersRepository.save(user);
        Optional<Users> findUser = usersRepository.findById(1l);

        assertThat(findUser.get()).isEqualTo(user);

    }
}