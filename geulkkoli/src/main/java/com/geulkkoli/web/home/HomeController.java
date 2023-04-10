package com.geulkkoli.web.home;

import com.geulkkoli.application.user.AuthUserAdaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/index")
    public String home(@AuthenticationPrincipal AuthUserAdaptor authUserAdaptor){
        log.info("nickName {}", authUserAdaptor.nickName());
        return "/index";
    }
}
