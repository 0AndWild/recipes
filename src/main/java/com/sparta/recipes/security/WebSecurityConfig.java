package com.sparta.recipes.security;

import com.sparta.recipes.filter.RestLoginFailureHandler;
import com.sparta.recipes.filter.RestLoginSuccessHandler;
import com.sparta.recipes.filter.RestLogoutSuccessHandler;
import com.sparta.recipes.filter.RestUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private RestLogoutSuccessHandler restLogoutSuccessHandler;

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
            http
//                    .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource()); //cors 활성화




        http.authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .mvcMatchers("/css/**","/scripts/**","/images/**").permitAll() //resources 파일 예외처리

                 /*회원 관리 처리 API 전부를 login 없이 허용*/
                .antMatchers("/user/register/**").permitAll()
                .antMatchers("/user/**").permitAll()

                /* 그 외 어떤 요청이든 '인증'*/
                .anyRequest().permitAll();


// [로그인 기능]

                http.formLogin().disable();
//// 로그인 View 제공 (GET /user/login)
                http.addFilterAt(getAuthenticationFilter(), RestUsernamePasswordAuthenticationFilter.class);
//                .usernameParameter("username")
// 로그인 처리 (POST /user/login)
                http.logout()
                        .logoutUrl("/user/logout")
                        .logoutSuccessHandler(restLogoutSuccessHandler)
                        .permitAll();
                http.exceptionHandling();
    }
    protected RestUsernamePasswordAuthenticationFilter getAuthenticationFilter(){
        RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
        try{
            authFilter.setFilterProcessesUrl("/user/login"); // 로그인에 대한 POST 요청을 받을 url을 정의합니다. 해당 코드가 없으면 정상적으로 작동하지 않습니다.
            authFilter.setUsernameParameter("username");
            authFilter.setPasswordParameter("password");
            authFilter.setAuthenticationManager(this.authenticationManagerBean());
            authFilter.setAuthenticationSuccessHandler(successHandler());
            authFilter.setAuthenticationFailureHandler(failureHandler());

        } catch (Exception e){
            e.printStackTrace();
        }
        return authFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.addAllowedOrigin("http://localhost:3000"); // local 테스트 시
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        configuration.addAllowedOriginPattern("http://localhost:3000"); // 배포 전 모두 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new RestLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new RestLoginFailureHandler();
    }

    
}
