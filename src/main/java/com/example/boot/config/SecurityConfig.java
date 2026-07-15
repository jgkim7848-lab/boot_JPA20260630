package com.example.boot.config;

import com.example.boot.security.CustomUserService;
import com.example.boot.security.LoginFailuerHandler;
import com.example.boot.security.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf (cross-site request forgery) : 인증된 사용자의 브라우저를 속여 사용자의
        // 의지와는 무관하게 특정 웹 사이트에 악의적인 요청(비밀번호변경, 계좌이체, 게시글 삭제)
        // 강제로 보내게 하는 사이버 공격
        // 방어 방법 csrf 토큰 사용 (개발단계에서는 막아놓고 나중에 배포단계에서 해지)
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/list").hasAnyRole("ADMIN")
                        .requestMatchers("/", "/dist/**","/css/**","/js/**","/image/**",
                                "/upload/**", "/board/list","/board/detail","/comment/list/**",
                                "/user/join", "/user/login","/error/**").permitAll()
                        // 나머지 경로는 로그인한 사용자만 허용
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .loginPage("/user/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                )
                .build();
    }

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler(){
        return new LoginFailuerHandler();
    }

}