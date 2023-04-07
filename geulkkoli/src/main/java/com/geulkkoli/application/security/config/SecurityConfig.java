package com.geulkkoli.application.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//시큐리티 설정파일
@Configuration
public class SecurityConfig {

    //루트 페이지는 인증 없이 접속 가능
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((auth) -> {
            auth.antMatchers("/").permitAll();
        });
        http.csrf().disable();
        http.formLogin()
                .loginPage("/login") // 직접 만든 로그인 페이지를 보여준다.
                .loginProcessingUrl("/login") //로그인 정보를 보낼 URL
                .failureUrl("/login") //실패시 URL 정보
                .usernameParameter("email")//아이디 파라미터 키 이름을 email로 바꿔준다.
                .passwordParameter("password");

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //메모리에 저장하는 spring security 유저 방식
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder().username("tako@naver.com")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}


