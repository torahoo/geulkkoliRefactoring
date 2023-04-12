package com.geulkkoli.domain.user.service;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.edit.EditForm;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@SpringBootTest
@Transactional
@Slf4j
class UpdateServiceTest {

    @Autowired
    EditService editService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.save( User.builder()
                .email("tako1@naver.com")
                .userName("김1")
                .nickName("바나나1")
                .password("123412")
                .phoneNo("01012345671")
                .gender("male")
                .build());

    }




    @Test
    @DisplayName("업데이트 테스트")
    void updateTest(){
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        log.info("@Test ::1 one={}", userRepository.findById(1L));

        //when
        EditForm preupdateUser = EditForm.builder()
                .userName("김1")
                .nickName("바나나155")
                .phoneNo("01055554646")
                .gender("female")
                .build();

        editService.update(1L, preupdateUser, mockHttpServletRequest);

        Optional<User> one = userRepository.findById(1L);

        log.info("@Test ::2 one={}", one);

        // then
        Assertions.assertThat("바나나155").isEqualTo(one.get().getNickName());
    }

}
