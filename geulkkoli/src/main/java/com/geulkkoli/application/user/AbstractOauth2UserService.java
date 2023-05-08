package com.geulkkoli.application.user;

import com.geulkkoli.application.security.RoleRepository;
import com.geulkkoli.domain.user.User;
import com.geulkkoli.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j

@Service
public abstract class AbstractOauth2UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User join(ProviderUser providerUser) {
        Optional<User> signUpUser = userRepository.findByEmail(providerUser.getEmail());
        if (signUpUser.isEmpty()) {
            log.info("회원가입");
            User user = User.builder()
                    .userName((providerUser.getUsername()))
                    .email(providerUser.getEmail())
                    .nickName("kakao" + providerUser.getUsername())
                    .password(passwordEncoder.encode("1234"))
                    .gender(providerUser.getGender())
                    .phoneNo("010-0000-0000")
                    .build();
            return userRepository.save(user);
        }

        return signUpUser.get();
    }
}
