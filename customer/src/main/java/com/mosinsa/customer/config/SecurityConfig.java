package com.mosinsa.customer.config;

import com.mosinsa.customer.service.CustomerService;
import com.mosinsa.customer.web.filter.AuthenticationFilter;
import com.mosinsa.customer.web.filter.JwtLogoutHandler;
import com.mosinsa.customer.web.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomerService customerService;
	private final JwtUtils jwtUtils;

    private static final String[] AUTH_WHITELIST = {
            "/**", "/h2-console/**", "/error"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

		return http
                .csrf().disable()
                .headers(authorize -> authorize.frameOptions().disable())
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
                                throw new RuntimeException(e);
                            }
                        }
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

	@Bean
	public AuthenticationFilter getAuthenticationFilter(AuthenticationManager manager) {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(customerService, jwtUtils);
		authenticationFilter.setAuthenticationManager(manager);
		return authenticationFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public LogoutHandler jwtLogoutHandler(){
		return new JwtLogoutHandler();
	}

}
