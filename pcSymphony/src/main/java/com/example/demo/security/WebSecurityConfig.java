package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    //로그인 없이 접근 가능 경로
    private static final String[] PUBLIC_URLS = {
            "/"                     //root
            , "/member/join"  //회원가입
            ,"/community/list" //커뮤니티 리스트
            ,"/css/**", "/js/**", "/images/**" // 정적 리소스 허용
//            , "/member/login" //로그인폼
            //메인?, 글 작성 이외는 가능
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(author -> author
                        .requestMatchers(PUBLIC_URLS).permitAll() //인증 없이 접근 가능
//                                .anyRequest().permitAll() //모든 요청을 인증없이 접근 가능
                        .anyRequest().authenticated() //그 외 요청은 인증 요구, 로그인사용자만 접근 가능
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/loginForm")
                        .usernameParameter("id") //로그인 폼에서 사용자 이름필드의 파라미터 이름
                        .passwordParameter("password")
                        .loginProcessingUrl("/member/login") //로그인 폼 제출 url
                        .defaultSuccessUrl("/", true)
                        //로그인 성공 후 리다이렉트 할 기본 url, "/"(홈페이지)
                        .permitAll() //모든 사용자에게 접근 허용
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/") //리다이렉트 홈페에지로
                );

        http
                //다른 도메인에서 오는 요청 허용 보안기능 비활성화
                .cors(AbstractHttpConfigurer::disable)
                //csrf보호를 비활성화, 보안 요구사항에 따라 활성화 가능성
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}