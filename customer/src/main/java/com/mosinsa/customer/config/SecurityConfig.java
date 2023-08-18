package com.mosinsa.customer.config;

import com.mosinsa.customer.web.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {

	private final JwtUtils jwtUtils;
    private static final String[] AUTH_WHITELIST = {
            "/**", "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .headers(authorize -> authorize.frameOptions().disable())
                .authorizeHttpRequests(authorize -> {
                            try {
                                authorize
                                        .requestMatchers(AUTH_WHITELIST).permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .headers(header -> header
                                                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
