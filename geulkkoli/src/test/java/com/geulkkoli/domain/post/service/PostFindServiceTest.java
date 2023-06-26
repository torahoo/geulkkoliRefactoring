package com.geulkkoli.domain.post.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.post.dto.AddDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostFindServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostFindService postFindService;

    @Test
    @Transactional
    void getCreatedAts(){
        User user = User.builder()
                .email("created@ats.com")
                .userName("우하하")
                .gender("gender")
                .password("password")
                .phoneNo("phoneNo")
                .nickName("nickName")
                .build();

        LocalDate signUpDate = LocalDate.of(2022, 2, 2);
        user.setCreatedAtForCalendarTest(signUpDate);

        userRepository.save(user);

        AddDTO addDTO1 = AddDTO.builder()
                .title("달력")
                .postBody("요호")
                .nickName(user.getNickName())
                .build();
        LocalDateTime createAt1 = LocalDateTime.of(2022, 3, 7, 1, 1);
        user.writePost(addDTO1, createAt1);

        AddDTO addDTO2 = AddDTO.builder()
                .title("달력")
                .postBody("요호")
                .nickName(user.getNickName())
                .build();
        LocalDateTime createAt2 = LocalDateTime.of(2023, 4, 8, 12, 1);
        user.writePost(addDTO2, createAt2);

        List<LocalDate> createdAts = postFindService.getCreatedAts(user);

        assertThat(createdAts.size()).isEqualTo(2);
        assertThat(createdAts).contains(createAt1.toLocalDate());
        assertThat(createdAts).contains(createAt2.toLocalDate());

    }
}