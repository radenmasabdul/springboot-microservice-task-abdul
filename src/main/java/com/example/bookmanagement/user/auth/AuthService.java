package com.example.bookmanagement.user.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.bookmanagement.security.JwtService;
import com.example.bookmanagement.user.dto.request.LoginRequest;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthService(
    AuthenticationManager authenticationManager,
    JwtService jwtService) {
      
      this.authenticationManager = authenticationManager;
      this.jwtService = jwtService;
  }

  public String login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword())
      );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtService.generateToken(userDetails);
  }
}