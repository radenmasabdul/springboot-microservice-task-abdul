package com.example.bookmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(
    JwtAuthenticationFilter jwtAuthenticationFilter) {
      this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http) throws Exception {

    http
      .csrf(csrf -> csrf.disable())
      .cors(cors -> {})
      
      .sessionManagement(session -> session
        .sessionCreationPolicy(
          SessionCreationPolicy.STATELESS)
        )

      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          HttpMethod.POST,
          "/api/auth/login",
          "/api/auth/logout"
        ).permitAll()
        .requestMatchers(
          HttpMethod.GET,
          "/api/auth/me"
        ).authenticated()
        .requestMatchers("/api/test").permitAll()
        .anyRequest().authenticated()
      )
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(
          new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
        )
      )

      .formLogin(form -> form.disable())
      .httpBasic(basic -> basic.disable());

    http.addFilterBefore(
      jwtAuthenticationFilter,
      UsernamePasswordAuthenticationFilter.class);

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