package com.example.bookmanagement.book.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Getter 
@Setter 
@Entity 
@Table(name = "books")
public class Book {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String author;

  @Column(nullable = false, unique = true)
  private String isbn;

  @Column(name = "published_date")
  private LocalDate publishedDate;

  public Book(){
  }
}
