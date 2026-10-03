package com.example.bookmanagement.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.bookmanagement.user.entity.Status;
import com.example.bookmanagement.user.entity.User;
import com.example.bookmanagement.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException(
        "User with email " + email + " not found")
      );

    return org.springframework.security.core.userdetails.User
      .withUsername(user.getEmail())
      .password(user.getPassword())
      .roles(user.getRole().name())
      .disabled(user.getStatus() != Status.ACTIVE)
      .build();
  }
}