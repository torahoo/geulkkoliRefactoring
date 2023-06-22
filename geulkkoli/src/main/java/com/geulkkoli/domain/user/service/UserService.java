package com.geulkkoli.domain.user.service;

import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.security.RoleEntity;
import com.geulkkoli.application.security.RoleRepository;
import com.geulkkoli.application.user.PasswordService;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.social.SocialSignUpDto;
import com.geulkkoli.web.user.dto.JoinFormDto;
import com.geulkkoli.web.user.dto.edit.UserInfoEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public boolean isEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isNickNameDuplicate(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isPhoneNoDuplicate(String phoneNo) {
        return userRepository.findByPhoneNo(phoneNo).isPresent();
    }

    @Transactional
    public void edit(Long id, UserInfoEditDto userInfoEditDto) {
        userRepository.edit(id, userInfoEditDto);
    }


    @Transactional
    public User signUp(JoinFormDto form) {
        User user = userRepository.save(form.toEntity(PasswordService.passwordEncoder));
        RoleEntity roleEntity = user.addRole(Role.USER);
        roleRepository.save(roleEntity);
        return user;
    }

    // 가입 날짜 임의 추가용 메소드 (추후 제거)
    @Transactional
    public User signUp(JoinFormDto form, LocalDate localDate) {
        User user = form.toEntity(PasswordService.passwordEncoder);
        user.setCreatedAtForCalendarTest(localDate);
        userRepository.save(user);

        RoleEntity roleEntity = user.Role(Role.USER);
        roleRepository.save(roleEntity);
        return user;
    }

    @Transactional
    public User signUp(SocialSignUpDto signUpDto) {
        User user = userRepository.save(signUpDto.toEntity(PasswordService.passwordEncoder));
        RoleEntity roleEntity = user.Role(Role.USER);
        roleRepository.save(roleEntity);
        return user;
    }

    /*
     * 관리자 실험을 위한 임시 관리자 계정 추가용 메서드*/
    @Transactional
    public void signUpAdmin(JoinFormDto form) {
        User user = form.toEntity(PasswordService.passwordEncoder);
        RoleEntity roleEntity = user.addRole(Role.ADMIN);
        roleRepository.save(roleEntity);
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }

    @Transactional
    public User signUp(SocialSignUpDto signUpDto) {
        User user = signUpDto.toEntity(PasswordService.passwordEncoder);
        RoleEntity role = user.addRole(Role.USER);
        roleRepository.save(role);
        userRepository.save(user);
        return user;
    }
}
