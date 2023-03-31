package com.geulkkoli.domain.user.service;

import com.geulkkoli.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    public boolean idCheck(String joinId) {
        if (userRepository.findByUserId(joinId).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
