package com.example.bookmanagement.book.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

@Getter 
@Setter 
public class BookResponse {
  private UUID id;
  private String title;
  private String author;
  private String isbn;
  private LocalDate publishedDate;
  private UUID createdBy;
  private UUID updatedBy;
  private Instant createdAt;
  private Instant updatedAt;
}
