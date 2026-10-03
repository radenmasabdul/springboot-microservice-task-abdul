package com.example.bookmanagement.book.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmanagement.book.dto.request.CreateBookRequest;
import com.example.bookmanagement.book.dto.request.PatchBookRequest;
import com.example.bookmanagement.book.dto.request.UpdateBookRequest;
import com.example.bookmanagement.book.dto.response.BookResponse;
import com.example.bookmanagement.book.service.BookService;
import com.example.bookmanagement.common.dto.ApiResponse;
import com.example.bookmanagement.common.util.ResponseHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/books")
public class BookController {
  
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks(
    @RequestParam(required = false) String search,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    
    return ResponseHandler.okPage(
      "Books retrieved successfully",
      bookService.getAllBooks(search, page, size)
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<BookResponse>> getBookById(
    @PathVariable Long id
  ) {
    
    return ResponseHandler.ok(
      "Book details found",
      bookService.getBookById(id)
    );
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<BookResponse>> createBook(
    @Valid @RequestBody CreateBookRequest request
  ) {

    return ResponseHandler.created(
      "New book successfully added", 
      bookService.createBook(request)
    );
  }
  
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<BookResponse>> updateBook(
    @PathVariable Long id,
    @Valid @RequestBody UpdateBookRequest request
  ) {

    return ResponseHandler.ok(
      "Book data successfully updated", 
      bookService.updateBook(id, request)
    );
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<BookResponse>> updatePartialBook(
    @PathVariable Long id,
    @Valid @RequestBody PatchBookRequest request
  ) {

    return ResponseHandler.ok(
      "Book data successfully updated",
      bookService.updatePartialBook(id, request)
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<BookResponse>> deleteBook(
    @PathVariable Long id
  ) {

    bookService.deleteBook(id);

    return ResponseHandler.ok(
      "Book deleted successfully",
      null
    );
  }
}
