package com.example.booklibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // API용으로 CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/books/**").authenticated()  // 책 API는 인증 필요
                .requestMatchers("/h2-console/**").permitAll()     // H2 콘솔은 누구나 접근 가능
                .requestMatchers("/actuator/**").hasRole("ADMIN")  // Actuator는 ADMIN만 접근 가능
                .anyRequest().authenticated()                       // 그 외 요청은 인증 필요
            )
            .httpBasic(httpBasic -> {})  // HTTP Basic 인증 활성화
            .headers(headers -> headers.frameOptions().disable());  // H2 콘솔 사용을 위한 설정
        
        return http.build();
    }
    
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}