package com.example.bookmanagement.book.dto.response;

import java.time.LocalDate;

import lombok.*;

@Getter 
@Setter 
public class BookResponse {
  private Long id;
  private String title;
  private String author;
  private String isbn;
  private LocalDate publishedDate;
}
