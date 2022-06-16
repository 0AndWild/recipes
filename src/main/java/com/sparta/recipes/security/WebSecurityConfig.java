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
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.concurrent.ConcurrentHashMap;

@EnableSpringHttpSession
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    /*SessionRepository<MapSession> 저장소 빈을 제공*/
    @Bean
    MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }


    //세션쿠키 커스텀
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("TOUGHCOOKIE");
        serializer.setCookiePath("/");
//        serializer.setDomainName("doridori.shop");
        serializer.setUseHttpOnlyCookie(false);
//        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        serializer.setSameSite("");
//        serializer.setUseSecureCookie(true);
        return serializer;
    }


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
            http.headers().frameOptions().sameOrigin();





        http.authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .mvcMatchers("/css/**","/scripts/**","/images/**").permitAll() //resources 파일 예외처리

                 /*회원 관리 처리 API 전부를 login 없이 허용*/
//                .antMatchers("/user/register/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/api/**").permitAll()
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
//                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(restLogoutSuccessHandler)
                        .deleteCookies()
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

        configuration.addAllowedMethod(HttpMethod.GET.name());
        configuration.addAllowedMethod(HttpMethod.HEAD.name());
        configuration.addAllowedMethod(HttpMethod.POST.name());
        configuration.addAllowedMethod(HttpMethod.PUT.name());
        configuration.addAllowedMethod(HttpMethod.DELETE.name());
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
//        configuration.addExposedHeader("refreshToken");
        configuration.addAllowedOriginPattern("http://localhost:3000"); // local 테스트 시
//        configuration.addAllowedOriginPattern("http://doridori.shop"); // 배포 전 모두 허용
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
