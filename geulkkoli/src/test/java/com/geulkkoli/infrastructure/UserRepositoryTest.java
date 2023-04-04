package com.geulkkoli.infrastructure;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.exception.EmptyDataException;
import org.junit.jupiter.api.DisplayName;
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
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .phoneNo("01012345678")
                .build();

        User saveUser = userRepository.save(user);

        assertThat(saveUser).isEqualTo(user);
    }

    @Test
    void findById() {
        User user = User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .phoneNo("01012345678")
                .build();

        userRepository.save(user);
        User findUser = userRepository.findById(1L)
                .orElseThrow(() -> new EmptyDataException("해당 데이터가 존제하지 않습니다."));

        assertThat(findUser).isEqualTo(user);
    }

    @Test
    void findByLoginId() {
        User user = User.builder()
                .email("tako@naver.com")
                .userName("김")
                .nickName("바나나")
                .password("1234")
                .phoneNo("01012345678")
                .build();

        userRepository.save(user);
        User findByLoginIdUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EmptyDataException("해당 데이터가 존제하지 않습니다."));

        assertThat(findByLoginIdUser).isEqualTo(user);
    }

    @Test
    @DisplayName("이메일로 회원 조회")
    void findByEmail() {
        User findByEmailUser = userRepository.findByEmail("tako@naver.com")
                .orElseThrow(() -> new EmptyDataException("해당 데이터가 존재하지 않습니다."));

        assertThat(findByEmailUser.getEmail()).isEqualTo("tako@naver.com");
    }

    @Test
    @DisplayName("닉네임으로 회원 조회")
    void findByNickName() {
        User findByNickNameUser = userRepository.findByNickName("바나나")
                .orElseThrow(() -> new EmptyDataException("해당 데이터가 존재하지 않습니다."));

        assertThat(findByNickNameUser.getEmail()).isEqualTo("tako@naver.com");
    }

}