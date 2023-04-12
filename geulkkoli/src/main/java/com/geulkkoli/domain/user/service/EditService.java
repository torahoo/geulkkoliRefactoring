package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.SessionConst;
import com.geulkkoli.web.user.edit.EditPasswordForm;
import com.geulkkoli.web.user.edit.EditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EditService {

    private final UserRepository userRepository;

    public boolean isNickNameDuplicate(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    public boolean isPhoneNoDuplicate(String phoneNo) {
        return userRepository.findByPhoneNo(phoneNo).isPresent();
    }


    public void update(Long id, EditForm editForm, HttpServletRequest httpServletRequest) {
        userRepository.update(id, editForm);

        Optional<User> byId = userRepository.findById(id);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, byId.get());
    }

    // 현재 비밀번호 입력 시 기존 비밀번호와 일치하는지 확인
    public boolean isPasswordVerification(User user, EditPasswordForm editPasswordForm) {
        return user.matchPassword(editPasswordForm.getPassword());
    }


    public void updatePassword(Long id, EditPasswordForm editPasswordForm) {
        userRepository.updatePassword(id, editPasswordForm);
    }

}
