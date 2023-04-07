package com.geulkkoli.web.home;

import com.geulkkoli.domain.user.User;
import com.geulkkoli.web.user.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/index")
    public String home(HttpSession session) {

        Object user = session.getAttribute(SessionConst.LOGIN_USER);

        // Optional로 변환하여 로그인 유지 확인
        if (!Objects.isNull(user)) {
            Optional<User> loginUser = (Optional<User>) user;
            log.info("{}에서 로그인", loginUser.get().getEmail());
            session.setAttribute("status", true);
            return "/index";
        }

        session.setAttribute("status", false);
        return "/index";
    }
}

