package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import com.geulkkoli.web.user.JoinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    public boolean checkDuplicate(JoinForm form, Model model){
        boolean check = false;
        if(userRepository.findByEmail(form.getEmail()).isPresent()){
            model.addAttribute("emailDuple", true);
            check = true;
        }
        if(userRepository.findByNickName(form.getNickName()).isPresent()){
            model.addAttribute("nickNameDuple", true);
            check = true;
        }
        return check;
    }

    public boolean idCheck(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean emailCheck(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean nickNameCheck(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    public void joinUser(JoinForm user){
        save(user.toEntity());
    }

    public void save(User user){
        userRepository.save(user);
    }



}
