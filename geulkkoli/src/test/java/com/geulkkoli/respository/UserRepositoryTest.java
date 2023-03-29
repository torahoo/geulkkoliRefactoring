package com.geulkkoli.respository;

import com.geulkkoli.domain.User;
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
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        User saveUser = usersRepository.save(user);

        assertThat(user).isEqualTo(user);
    }

    @Test
    void findById() {
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        usersRepository.save(user);
        Optional<User> findUser = usersRepository.findById(1l);

        assertThat(findUser.get()).isEqualTo(user);

    }
}