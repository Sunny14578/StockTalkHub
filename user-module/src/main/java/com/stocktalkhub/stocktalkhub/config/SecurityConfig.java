package com.stocktalkhub.stocktalkhub.config;

import com.stocktalkhub.stocktalkhub.security.jwt.exception.CustomAuthenticationEntryPoint;
import com.stocktalkhub.stocktalkhub.security.jwt.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user-module/members/join", "/user-module/members/login",
                        "/user-module/**").permitAll()

//                .requestMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers("/members/emailCheck").permitAll()
//                .antMatchers("/logout/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()// Security에서 인증중 에러가 난다면
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .formLogin().disable();// 어떤 엔트리포인트로 가도록 설정할 수 있다.
    }

}
