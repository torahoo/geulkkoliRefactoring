package com.geulkkoli.application.security.handler;

import com.geulkkoli.application.security.Role;
import com.geulkkoli.application.user.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Transactional
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUser principal = (AuthUser) authentication.getPrincipal();
        principal.getAuthorities().stream().map(auth -> auth.getAuthority()).findAny().ifPresent(role -> {
            log.info(role);
            if (Role.isGuest(role)) {
                try {
                    response.sendRedirect("/social/oauth2/signup");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    response.sendRedirect("/");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
