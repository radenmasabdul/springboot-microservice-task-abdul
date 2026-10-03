package com.example.bookmanagement.book.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter 
@Setter 
public class CreateBookRequest {
  
  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Author is required")
  private String author;

  @NotBlank(message = "ISBN is required")
  private String isbn;

  @NotNull(message = "Published date is required")
  private LocalDate publishedDate;

}
