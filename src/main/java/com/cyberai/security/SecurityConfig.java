package com.cyberai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(a -> a

                        .requestMatchers(
                                "/css/**",
                                "/login",
                                "/access-denied"
                        )
                        .permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/**")
                        .hasAnyRole("ADMIN", "ANALYST")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(f -> f
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .exceptionHandling(e -> e
                        .accessDeniedPage("/access-denied")
                )

                .logout(l -> l
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}