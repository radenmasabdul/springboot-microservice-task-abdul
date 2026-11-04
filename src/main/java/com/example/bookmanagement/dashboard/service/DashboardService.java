package com.example.bookmanagement.dashboard.service;

import com.example.bookmanagement.book.repository.BookRepository;
import com.example.bookmanagement.dashboard.dto.response.DashboardResponse;
import com.example.bookmanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  public DashboardService(
    BookRepository bookRepository,
    UserRepository userRepository
  ) {
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  public DashboardResponse getDashboard() {
    long totalBooks = bookRepository.count();
    long totalUsers = userRepository.count();

    List<DashboardResponse.RecentBook> recentBooks =
      bookRepository.findTop5ByOrderByCreatedAtDesc()
        .stream()
        .map(book -> new DashboardResponse.RecentBook(
          book.getTitle(),
          book.getAuthor(),
          book.getIsbn(),
          book.getCreatedBy().getName(),
          book.getCreatedAt().toString()
        ))
        .toList();

    return new DashboardResponse(
      totalBooks,
      totalUsers,
      recentBooks,
      List.of()
    );
  }
}
