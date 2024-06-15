package com.mosinsa.customer.common.config;

import com.mosinsa.customer.application.CustomerService;
import com.mosinsa.customer.common.jwt.Token;
import com.mosinsa.customer.ui.filter.AuthenticationFilter;
import com.mosinsa.customer.ui.filter.JwtLogoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomerService customerService;

    private final Map<String, Token> tokenUtilMap;
    private static final String[] AUTH_WHITELIST = {
            "/**", "/h2-console/**", "/error"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        return http
                .csrf().disable()
                .headers(authorize -> authorize.frameOptions().disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                            try {
                                authorize
                                        .requestMatchers(AUTH_WHITELIST).permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .logout()
                                        .addLogoutHandler(jwtLogoutHandler())
                                        .logoutSuccessHandler((request, response, authentication) -> {
                                            response.getWriter().print("logout success");
                                            response.getWriter().close();
                                        })
                                        .and()
                                        .addFilter(getAuthenticationFilter(authenticationManager));
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        }
                )
                .build();
    }

    @Bean
    public AuthenticationFilter getAuthenticationFilter(AuthenticationManager manager) {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customerService, tokenUtilMap);
        authenticationFilter.setAuthenticationManager(manager);
        return authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public LogoutHandler jwtLogoutHandler() {
        return new JwtLogoutHandler(tokenUtilMap);
    }
}
