package com.example.bookmanagement.user.dto.request;

import com.example.bookmanagement.user.entity.Role;
import com.example.bookmanagement.user.entity.Status;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  private Role role;

  private Status status;
}