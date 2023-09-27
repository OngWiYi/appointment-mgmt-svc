/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.configs;

import com.example.appointmentmgmtsvc.repositories.UserRepository;
import com.example.appointmentmgmtsvc.securities.AuthEntryPointJwt;
import com.example.appointmentmgmtsvc.securities.JWTFilter;
import com.example.appointmentmgmtsvc.securities.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    private JWTFilter getJwtFilter() {
        return applicationContext.getBean(JWTFilter.class);
    }

    private UserDetailsServiceImpl getUserDetailsService() {
        return applicationContext.getBean(UserDetailsServiceImpl.class);
    }

    private AuthEntryPointJwt getAuthEntryPointJwt() {
        return applicationContext.getBean(AuthEntryPointJwt.class);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(getAuthEntryPointJwt()).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
            .requestMatchers("/api/v0/auth/**").permitAll().requestMatchers("/error").permitAll().anyRequest()
            .authenticated();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(getUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
