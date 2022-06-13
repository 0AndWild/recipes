package com.sparta.recipes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");


    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
// 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
            http.csrf().disable();
            http.httpBasic().disable()
                .cors(); //cors 활성화




        http.authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

////                .antMatchers("/rest/api/**" ).permitAll()
//                .mvcMatchers("/css/**","/scripts/**","/images/**").permitAll() //resources 파일 예외처리

// 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/user/register/**").permitAll()

                .antMatchers("/user/**").permitAll()
//                .antMatchers("/").permitAll();
// 그 외 어떤 요청이든 '인증'
                .anyRequest().permitAll()

                .and()
// [로그인 기능]

                .formLogin()
//// 로그인 View 제공 (GET /user/login)
                .loginPage("/login")
//                .usernameParameter("username")
// 로그인 처리 (POST /user/login)
                .loginProcessingUrl("/user/login")
// 로그인 처리 후 성공 시 URL
                .defaultSuccessUrl("http://localhost:3000")
// 로그인 처리 후 실패 시 URL
                .failureUrl("/user/login?error")
                .permitAll();
//                .and()
//// [로그아웃 기능]
//                .logout()
//// 로그아웃 처리 URL (GET/ user/logout)
//                .logoutUrl("/user/logout")
//                .logoutSuccessUrl("/")
//                .permitAll();
    }


    
}
