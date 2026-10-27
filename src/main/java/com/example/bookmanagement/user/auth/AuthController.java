package com.example.bookmanagement.user.auth;
import com.example.bookmanagement.user.dto.response.CurrentUserResponse;

import java.time.Duration;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bookmanagement.common.dto.ApiResponse;
import com.example.bookmanagement.common.util.ResponseHandler;
import com.example.bookmanagement.user.dto.request.LoginRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final String ACCESS_TOKEN_COOKIE = "token";
  private static final Duration COOKIE_DURATION = Duration.ofHours(8);

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Void>> login(
    @Valid @RequestBody LoginRequest request,
    HttpServletResponse response) {
      
    String accessToken = authService.login(request);
      
    ResponseCookie accessTokenCookie = createCookie(
      ACCESS_TOKEN_COOKIE,
      accessToken,
      COOKIE_DURATION
    );

    response.addHeader(
      HttpHeaders.SET_COOKIE,
      accessTokenCookie.toString()
    );

    return ResponseHandler.ok(
      "Login successful",
      null
    );
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
    HttpServletResponse response) {

    ResponseCookie expiredCookie = createCookie(
      ACCESS_TOKEN_COOKIE,
      "",
      Duration.ZERO
    );

    response.addHeader(
      HttpHeaders.SET_COOKIE,
      expiredCookie.toString()
    );

    return ResponseHandler.ok(
      "Logout successful",
      null
    );
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<CurrentUserResponse>> getCurrentUser(
    Authentication authentication
  ) {
    CurrentUserResponse currentUser = authService.getCurrentUser(authentication);

    return ResponseHandler.ok(
      "Current user retrieved successfully",
      currentUser
    );
  }

  private ResponseCookie createCookie(
    String name,
    String value,
    Duration maxAge
  ) {
    
    return ResponseCookie.from(name, value)
      .httpOnly(true)
      .secure(true)
      .path("/")
      .maxAge(maxAge)
      .sameSite("None")
      .build();
  }
}