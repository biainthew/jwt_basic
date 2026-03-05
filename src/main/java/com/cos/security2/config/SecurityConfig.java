package com.cos.security2.config;

import com.cos.security2.filter.MyFilter2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MyFilter2(), BasicAuthenticationFilter.class);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement)
                        -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(corsFilter)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                        .requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
