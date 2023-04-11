package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.edit.EditPasswordForm;
import com.geulkkoli.web.user.edit.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EditService {

    private final UserRepository userRepository;

    public boolean isNickNameDuplicate(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    public boolean isPhoneNoDuplicate(String phoneNo) {
        return userRepository.findByPhoneNo(phoneNo).isPresent();
    }

    public void update(User user, EditForm editForm) {
        userRepository.update(user, editForm);
    }

    public boolean isPasswordVerification(User user, EditPasswordForm editPasswordForm) {
        return user.matchPassword(editPasswordForm.getPassword());
    }

    public void updatePassword(User user, EditPasswordForm editPasswordForm) {
        userRepository.updatePassword(user, editPasswordForm);
    }

}
