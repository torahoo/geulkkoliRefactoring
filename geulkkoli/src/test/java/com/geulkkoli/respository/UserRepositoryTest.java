package com.geulkkoli.respository;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
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
    private UserRepository userRepository;

    @Test
    void save() {
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        User saveUser = userRepository.save(user);

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

        userRepository.save(user);
        Optional<User> findUser = userRepository.findById(1l);

        assertThat(findUser.get()).isEqualTo(user);

    }

    @Test
    void findByUserId() {
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        userRepository.save(user);
        Optional<User> findUser = userRepository.findByUserId("kkk");

        assertThat(findUser.get()).isEqualTo(user);
    }
}