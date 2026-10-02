package com.example.bookmanagement.user.auth;

import java.time.Duration;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.example.bookmanagement.common.dto.ApiResponse;
import com.example.bookmanagement.common.util.ResponseHandler;
import com.example.bookmanagement.user.dto.request.LoginRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final String ACCESS_TOKEN_COOKIE = "token";

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Void>> login(
    @Valid @RequestBody LoginRequest request,
    HttpServletResponse response,
    CsrfToken csrfToken) {
      csrfToken.getToken();
      
      String accessToken = authService.login(request);
      
      Cookie accessTokenCookie = createCookie(
        ACCESS_TOKEN_COOKIE,
        accessToken,
        (int) Duration.ofHours(8).toSeconds()
      );

      response.addCookie(accessTokenCookie);

    return ResponseHandler.ok(
      "Login successful",
      null
    );
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
    HttpServletResponse response) {
      
      clearCookie(
        response,
        ACCESS_TOKEN_COOKIE
      );

      return ResponseHandler.ok(
        "Logout successful",
        null
      );
  }

  private Cookie createCookie(
    String name,
    String value,
    int maxAge) {
      Cookie cookie = new Cookie(name, value);

      cookie.setHttpOnly(true);
      cookie.setSecure(false);
      cookie.setPath("/");
      cookie.setMaxAge(maxAge);

    return cookie;
  }

  private void clearCookie(
    HttpServletResponse response,
    String name) {
      
      Cookie cookie = new Cookie(name, "");

      cookie.setHttpOnly(true);
      cookie.setSecure(false);
      cookie.setPath("/");
      cookie.setMaxAge(0);

    response.addCookie(cookie);
  }
}