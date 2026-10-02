package com.example.bookmanagement.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bookmanagement.common.dto.ApiResponse;
import com.example.bookmanagement.common.util.ResponseHandler;

@RestControllerAdvice 
public class GlobalExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
    return ResponseHandler.buildError(
      exception.getStatus(),
      exception.getMessage()
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception exception) {
    return ResponseHandler.internalServerError(
      "Internal server error"
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException exception) {
    String message = exception.getBindingResult().getFieldErrors().stream()
      .findFirst()
      .map(error -> error.getDefaultMessage())
      .orElse("Validation failed");

    return ResponseHandler.buildError(HttpStatus.BAD_REQUEST, message);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
    return ResponseHandler.buildError(
      HttpStatus.CONFLICT,
      "Data already exists or violates a database constraint"
    );
  }
}
