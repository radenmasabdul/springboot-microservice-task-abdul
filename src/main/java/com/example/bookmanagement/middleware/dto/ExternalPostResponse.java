package com.example.bookmanagement.middleware.dto;

public record ExternalPostResponse(
  Long userId,
  Long id,
  String title,
  String body
) {}
