package com.example.bookmanagement.user.dto.request;

import com.example.bookmanagement.user.entity.Role;
import com.example.bookmanagement.user.entity.Status;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
public class CreateUserRequest {
  
  @NotBlank(message = "Email is required")
  @Email(message = "Email format is invalid")
  private String email;
  
  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @NotNull(message = "Role is required")
  private Role role;

  @NotNull(message = "Status is required")
  private Status status;
}
