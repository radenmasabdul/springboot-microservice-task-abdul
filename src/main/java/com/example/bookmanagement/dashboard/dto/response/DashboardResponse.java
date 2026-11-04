package com.example.bookmanagement.dashboard.dto.response;

import java.util.List;

public record DashboardResponse (
  long totalBooks,
  long totalUsers,
  List<RecentBook> recentBooks,
  List<MonthlyStatistic> monthlyStatistics
) {

  public record RecentBook(
    String title,
    String author,
    String isbn,
    String createdBy,
    String createdAt
  ) {}

  public record MonthlyStatistic(
    String month,
    long total
  ) {}

}
