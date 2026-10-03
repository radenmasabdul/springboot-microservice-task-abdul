package com.example.bookmanagement.book.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.bookmanagement.book.dto.request.CreateBookRequest;
import com.example.bookmanagement.book.dto.request.PatchBookRequest;
import com.example.bookmanagement.book.dto.request.UpdateBookRequest;
import com.example.bookmanagement.book.dto.response.BookResponse;
import com.example.bookmanagement.book.entity.Book;
import com.example.bookmanagement.book.repository.BookRepository;
import com.example.bookmanagement.common.exception.AppException;
import com.example.bookmanagement.common.mapper.BookMapper;
import com.example.bookmanagement.common.specification.GenericSpecification;
import com.example.bookmanagement.common.util.RepositoryUtils;

@Service
public class BookService {
  
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final IsbnService isbnService;

  public BookService(
    BookRepository bookRepository,
    BookMapper bookMapper,
    IsbnService isbnService
  ) {
      this.bookRepository = bookRepository;
      this.bookMapper = bookMapper;
      this.isbnService = isbnService;
  }

  public BookResponse createBook(CreateBookRequest request) {
    Book book = bookMapper.toEntity(request);

    String isbn = isbnService.generateValidIsbn(request.getIsbn());
    book.setIsbn(isbn);

    Book savedBook = bookRepository.save(book);

    return bookMapper.toResponse(savedBook);
  }

  public Page<BookResponse> getAllBooks(String search, int page, int size) {
    List<String> columnToSearch = Arrays.asList("title", "author", "isbn");

    Specification<Book> spec = Specification.<Book>where(
      GenericSpecification.searchByColumn(search, columnToSearch)
    );

    PageRequest pageable = PageRequest.of(page, size);
    Page<Book> bookPage = bookRepository.findAll(spec, pageable);

    return  bookPage.map(bookMapper::toResponse);
  }

  public BookResponse getBookById(Long id) {
    Book book = RepositoryUtils.findOrThrow(bookRepository, id, "Book");
    return bookMapper.toResponse(book);
  }

  public BookResponse updateBook(Long id, UpdateBookRequest request) {
    Book book = RepositoryUtils.findOrThrow(bookRepository, id, "Book");

    bookMapper.updateEntityFromRequest(request, book);

    String isbn = isbnService.generateValidIsbn(request.getIsbn());
    book.setIsbn(isbn);

    Book updateBook = bookRepository.save(book);

    return bookMapper.toResponse(updateBook);
  }

  public BookResponse updatePartialBook(Long id, PatchBookRequest request) {
    Book book = RepositoryUtils.findOrThrow(bookRepository, id, "Book");

    bookMapper.patchEntityFromRequest(request, book);

    if (request.getIsbn() != null) {
      String isbn = isbnService.generateValidIsbn(request.getIsbn());
      book.setIsbn(isbn);
    }

    Book updatePartialBook = bookRepository.save(book);

    return bookMapper.toResponse(updatePartialBook);
  }

  public void deleteBook(Long id) {
    if (!bookRepository.existsById(id)) {
      throw new AppException(HttpStatus.NOT_FOUND, "Book with ID " + id + " was not found");
    }
    bookRepository.deleteById(id);
  }
}
