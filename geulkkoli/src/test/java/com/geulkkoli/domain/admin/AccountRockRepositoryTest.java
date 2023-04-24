package com.geulkkoli.domain.admin;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AccountRockRepositoryTest {

    @Autowired
    private AccountRockRepository accountRockRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    void findByUserId() {
        //given
        User user = User.builder()
                .userName("김")
                .nickName("김김")
                .gender("M")
                .password("XXXX")
                .phoneNo("01012345678")
                .email("ttt@gmail.com")
                .build();
        User user1 = userRepository.save(user);
        AccountLock accountLock = AccountLock.of(user1, "정지사유", LocalDateTime.of(2023, 5, 11, 22, 22, 22));

        accountRockRepository.save(accountLock);
        //then
        AccountLock lockedUser = accountRockRepository.findByUserId(1L);

        assertEquals(lockedUser.getLockedUser().getUserId(), 1L);
    }



}