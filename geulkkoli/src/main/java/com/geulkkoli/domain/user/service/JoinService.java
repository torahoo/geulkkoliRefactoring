package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    public boolean joinUser(JoinForm form, BindingResult bindingResult) {
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "Duple.joinForm.email");
        }

        if (userRepository.findByNickName(form.getNickName()).isPresent()) {
            bindingResult.rejectValue("nickName", "Duple.joinForm.nickName");
        }

        if(!form.getPassword().equals(form.getVerifyPassword())){
            bindingResult.rejectValue("verifyPassword", "Duple.joinForm.verifyPassword");
        }

        if(!bindingResult.hasErrors()){
            save(form.toEntity());
        }

        return bindingResult.hasErrors();
    }

    public void save(User user) {
        userRepository.save(user);
    }


}
