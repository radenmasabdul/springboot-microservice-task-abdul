package com.example.bookmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http) throws Exception {
      http
          .csrf(csrf -> csrf
              .csrfTokenRepository(
                  CookieCsrfTokenRepository.withHttpOnlyFalse())
              .ignoringRequestMatchers("/api/auth/login"))

          .sessionManagement(session -> session
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

          .authorizeHttpRequests(auth -> auth
              .requestMatchers(
                  "/api/auth/**",
                  "/api/test")
              .permitAll()
              .anyRequest()
              .authenticated())

          .formLogin(form -> form.disable())
          .httpBasic(basic -> basic.disable());
        
      http.addFilterBefore(
        jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
      );

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    UserDetailsService userDetailsService,
    PasswordEncoder passwordEncoder) {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);

      authenticationProvider.setPasswordEncoder(passwordEncoder);

      return new ProviderManager(authenticationProvider);
    }
}