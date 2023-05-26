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

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;

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

    @Transactional(readOnly = true)

    public void edit(Long id, UserInfoEditDto userInfoEditDto) {
        userRepository.edit(id, userInfoEditDto);
    }


    @Transactional
    public User signUp(JoinFormDto form) {
        User user = userRepository.save(form.toEntity(PasswordService.passwordEncoder));
        RoleEntity roleEntity = user.Role(Role.USER);
        roleRepository.save(roleEntity);
        return user;
    }


    /*
     * 관리자 실험을 위한 임시 관리자 계정 추가용 메서드*/
    @Transactional
    public void signUpAdmin(JoinFormDto form) {
        User user = form.toEntity(PasswordService.passwordEncoder);
        RoleEntity roleEntity = user.Role(Role.ADMIN);
        roleRepository.save(roleEntity);
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user found id matches:" + id));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUserNameAndPhoneNo(String userName, String phoneNo) {
        return userRepository.findByUserNameAndPhoneNo(userName, phoneNo);
    }

    public Optional<User> findByEmailAndUserNameAndPhoneNo(String email, String userName, String phoneNo) {
        return userRepository.findByEmailAndUserNameAndPhoneNo(email, userName, phoneNo);
    }

    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("No user found nickname matches:" + nickName));
    }

    @Transactional
    public User signUp(SocialSignUpDto signUpDto) {
        User user = userRepository.save(signUpDto.toEntity(PasswordService.passwordEncoder));
        RoleEntity roleEntity = user.Role(Role.USER);
        roleRepository.save(roleEntity);
        return user;
    }
}
