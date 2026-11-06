package com.example.bookmanagement.middleware.exception;

import com.example.bookmanagement.middleware.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MiddlewareExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(MiddlewareExceptionHandler.class);

  @ExceptionHandler(ExternalApiException.class)
  public ResponseEntity<ApiErrorResponse> handleExternalApiException(
    ExternalApiException exception,
    HttpServletRequest request
  ) {

    log.error("Middleware request {} failed while calling an external API: {}",
      request.getRequestURI(), exception.getMessage());

    ApiErrorResponse errorResponse = new ApiErrorResponse(
      Instant.now(),
      HttpStatus.BAD_GATEWAY.value(),
      HttpStatus.BAD_GATEWAY.getReasonPhrase(),
      "External api request failed",
      request.getRequestURI()
    );

    return ResponseEntity
      .status(HttpStatus.BAD_GATEWAY)
      .body(errorResponse);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFoundException(
    NoResourceFoundException exception,
    HttpServletRequest request
  ) {

    log.error(
      "Middleware endpoint not found: {} {}",
      request.getMethod(),
      request.getRequestURI()
    );

    ApiErrorResponse errorResponse = new ApiErrorResponse(
      Instant.now(),
      HttpStatus.NOT_FOUND.value(),
      HttpStatus.NOT_FOUND.getReasonPhrase(),
      "Requested endpoint does not exist: "
        + request.getMethod() + " " + request.getRequestURI(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(errorResponse);
  }
}
