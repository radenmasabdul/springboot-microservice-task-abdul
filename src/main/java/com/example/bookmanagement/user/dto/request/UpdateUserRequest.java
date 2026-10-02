package com.example.bookmanagement.user.dto.request;

import com.example.bookmanagement.user.entity.Role;
import com.example.bookmanagement.user.entity.Status;

import lombok.*;

@Getter 
@Setter 
public class UpdateUserRequest {
  private String password;

  private Role role;
  private Status status;
}
