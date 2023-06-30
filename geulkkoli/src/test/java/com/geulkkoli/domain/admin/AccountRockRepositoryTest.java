package com.geulkkoli.domain.admin;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountRockRepositoryTest {

    @Autowired
    private AccountLockRepository accountRockRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    void findById() {
        //given

        Optional<User> byId = userRepository.findById(2l);
        AccountLock accountLock = AccountLock.of(byId.get(), "정지사유", LocalDateTime.of(2023, 5, 11, 22, 22, 22));

        AccountLock save = accountRockRepository.save(accountLock);
        //then
        Optional<AccountLock> lockedUser = accountRockRepository.findByUserId(2L);

        assertThat(lockedUser).isIn(Optional.of(save)).hasValue(save);
        assertAll(
                () -> assertThat(lockedUser).isIn(Optional.of(save)),
                () -> assertThat(lockedUser).hasValue(save));
    }

    @DisplayName("정지 처분된 유저가 존재하는지 확인")
    @Transactional
    @Test
    void existsById() {
        //given
        Optional<User> byId = userRepository.findById(2L);
        AccountLock accountLock = AccountLock.of(byId.get(), "정지사유", LocalDateTime.of(2023, 5, 11, 22, 22, 22));

        AccountLock save = accountRockRepository.save(accountLock);
        //then
        Boolean aBoolean = accountRockRepository.existsByLockedUser_UserId(save.getLockedUser().getUserId());

        assertThat(aBoolean).isTrue();
    }
}