package com.example.bookmanagement.book.dto.request;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
public class PatchBookRequest {

  private String title;
  private String author;
  private String isbn;
  private LocalDate publishedDate;
  
}