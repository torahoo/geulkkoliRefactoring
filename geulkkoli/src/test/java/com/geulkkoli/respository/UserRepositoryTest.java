package com.geulkkoli.respository;

import com.geulkkoli.domain.User;
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
                .orElseThrow(() -> new EmptyDataException("해당 데이터가 존제하지 않습니다."));

        assertThat(findUser).isEqualTo(user);
    }

    @Test
    void findByLoginId() {
        User user = User.builder()
                .userId("kkk")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .build();

        userRepository.save(user);
        User findByLoginIdUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new EmptyDataException("해당 데이터가 존제하지 않습니다."));

        assertThat(findByLoginIdUser).isEqualTo(user);
    }
}