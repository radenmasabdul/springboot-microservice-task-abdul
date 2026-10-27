package com.example.bookmanagement.user.dto.response;

import java.util.List;

public record CurrentUserResponse(
  String name,
  String email,
  List<String> roles
) {}