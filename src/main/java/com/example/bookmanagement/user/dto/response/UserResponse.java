package com.example.bookmanagement.user.dto.response;

import com.example.bookmanagement.user.entity.Role;
import com.example.bookmanagement.user.entity.Status;

import lombok.*;

@Getter 
@Setter 
public class UserResponse {
  private Long id;
  private String email;
  private String name;
  private Role role;
  private Status status;
}
