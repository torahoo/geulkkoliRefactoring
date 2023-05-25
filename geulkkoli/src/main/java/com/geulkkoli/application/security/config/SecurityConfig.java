package com.geulkkoli.application.security.config;

import com.geulkkoli.application.security.handler.LoginFailureHandler;
import com.geulkkoli.application.security.handler.LoginSuccessHandler;
import com.geulkkoli.application.user.UserSecurityService;
import com.geulkkoli.application.social.CustomOauth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 시큐리티 설정파일
 */
@Configuration
@Slf4j
public class SecurityConfig {

    private final UserSecurityService userSecurityService;
    private final CustomOauth2UserService customOauth2UserService;


    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(UserSecurityService userSecurityService, CustomOauth2UserService customOauth2UserService) {
        this.userSecurityService = userSecurityService;
        this.customOauth2UserService = customOauth2UserService;
    }


    /**
     * 시큐리티 필터 설정
     * 루트 페이지, 로그인 페이지, css,js 경론는 인증 없이 접속 가능
     * csrf 공격 방지를 위한 설정을 끈다
     * 인증방식이 form방식인 걸 알려준다
     * 로그인 폼 페이즈가 어디인지 알려준다
     * 로그인 정보 URI가 어디인지 알려준다
     * 실패시 URL 정보
     * userName 키 이름을 email로 바꿔준다.
     * password의 키 이름을 password로 바꿔준다
     * 로그아웃에 관련된 처리이다
     * 로그아웃 진입 경로를 뜻한다
     * 로그아웃 성공시 경로를 뜻한다
     * 로그아웃 버튼을 누르면 세션에서 값이 사라지는 설정이다.
     * 로그아웃 성공시 세션을 무효화 시킨다.
     * auth mvcMatchers는 특정 경로에 대한 권한을 설정합니다.
     * permitAll()은 누구나 겁근 가능하다는 뜻입니다.
     */

    /**
     * how to integration with spring security and oauth2 login and form login
     * https://www.baeldung.com/spring-security-5-oauth2-login
     * https://www.baeldung.com/spring-security-5-form-login
     */
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((auth) -> {
                    auth.mvcMatchers("/admin/**").hasRole("ADMIN");
                    auth.mvcMatchers("/user/edit/**").hasAnyRole("USER", "GUEST");
                    auth.mvcMatchers("/post/add/**", "/post/update/**", "/post/delete/**").hasAnyRole("USER", "ADMIN");
                    auth.mvcMatchers(HttpMethod.GET, "/", "/loginPage", "/post/read/**", "/post/list/**", "/post/search/**", "/post/category/*")
                            .permitAll();
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll(); // 정적 리소스들(css,js)등을 권장 방식에 맞게 인증 체크에서 제외 시켰다
                }).csrf().disable()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login-process")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .oauth2Login(oauth -> {
                    oauth.userInfoEndpoint(
                            userInfoEndpointConfig -> {
                                userInfoEndpointConfig
                                        .userService(customOauth2UserService)
                                        .and()
                                        .successHandler(loginSuccessHandler)
                                        .failureHandler(loginFailureHandler)
                                ;
                            }
                    ).loginPage("/loginPage");
                }).userDetailsService(userSecurityService)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        return http.build();
    }

    /*
     * password를 복호화 해줄 때 사용한다.
     * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}



