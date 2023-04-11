package com.geulkkoli.application.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final MessageSource messageSource;

    public LoginFailureHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("email");
        log.info("email = {}",email);
        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = messageSource.getMessage("error.BadCredentialsException", null, Locale.KOREA);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = messageSource.getMessage("error.InternalAuthenticationServiceException", null, Locale.KOREA);
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = messageSource.getMessage("error.UsernameNotFoundException", null, Locale.KOREA);
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = messageSource.getMessage("error.AuthenticationCredentialsNotFoundException", null, Locale.KOREA);
        } else {
            errorMessage = messageSource.getMessage("error.OtherException", null, Locale.KOREA);
        }

        log.info("error message {}", errorMessage);

        request.setAttribute("loginError", errorMessage);

        request.getRequestDispatcher("/loginPage").forward(request,response);
    }
}
