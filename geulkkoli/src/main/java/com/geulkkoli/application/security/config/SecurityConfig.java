package com.geulkkoli.application.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 시큐리티 설정파일
 *
 */
@Configuration
public class SecurityConfig {

    /**
     * 시큐리티 설정파일
     * 루트 페이지는 인증 없이 접속 가능
     * 로그인 정보를 URL
     * 실패시 URL 정보
     * 아이디 키 이름을 email로 바꿔준다.
     * 비빌먼호 키 이름을 password로 바꿔준다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((auth) -> {
            auth.antMatchers("/","/css/**").permitAll();
        });
        http.csrf().disable();
        http.formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index")
                .failureUrl("/login/error")
                .usernameParameter("email")
                .passwordParameter("password");

        return http.build();
    }

    /*
    * password를 복호화 해줄 때 사용한다.
    * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //메모리에 저장하는 spring security 유저 방식


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


