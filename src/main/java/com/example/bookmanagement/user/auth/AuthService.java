package com.example.bookmanagement.user.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.bookmanagement.security.JwtService;
import com.example.bookmanagement.user.dto.request.LoginRequest;

import com.example.bookmanagement.user.entity.User;
import com.example.bookmanagement.user.repository.UserRepository;
import com.example.bookmanagement.user.dto.response.CurrentUserResponse;

import java.util.List;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;

  public AuthService(
    AuthenticationManager authenticationManager,
    JwtService jwtService,
    UserRepository userRepository
  ) {
      this.authenticationManager = authenticationManager;
      this.jwtService = jwtService;
      this.userRepository = userRepository;
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

  public CurrentUserResponse getCurrentUser(Authentication authentication) {
    User user = userRepository.findByEmail(authentication.getName())
      .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found"));

    List<String> roles = authentication.getAuthorities()
      .stream()
      .map(authority -> authority.getAuthority()
      .replaceFirst("^ROLE_", ""))
      .toList();

    return new CurrentUserResponse(
      user.getName(),
      user.getEmail(),
      roles
    );
  }
}