package com.geulkkoli.respository;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(saveUser).isEqualTo(user);
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
        User findUser = userRepository.findById(1L)
                .orElse(null);

        assertThat(findUser).isEqualTo(user);

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
        User findUser = userRepository.findByUserId("kkk")
                .orElse(null);

        assertThat(findUser).isEqualTo(user);
    }
}