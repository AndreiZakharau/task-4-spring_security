package com.epam.esm.security.configSecury;

import com.epam.esm.security.filter.JwtFilter;
import io.lettuce.core.support.caching.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.function.Consumer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        auth -> auth
                                .antMatchers("/api/v1.1/auth/login", "/api/v1.1/auth/token", "/api/v1.1/auth/refresh").permitAll()
                                .antMatchers(HttpMethod.POST, "/api/v1.1/users").permitAll()
                                .antMatchers(HttpMethod.GET, "/api/v1.1/certificates/**","/api/v1.1/tags/**").permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ).build();

    }

    @Bean
    public RedisCache redisCache(){
        return new RedisCache() {
            @Override
            public Object get(Object key) {
                return key;
            }

            @Override
            public void put(Object key, Object value) {
            }

            @Override
            public void addInvalidationListener(Consumer listener) {
            }

            @Override
            public void close() {
            }
        };
    }

}
