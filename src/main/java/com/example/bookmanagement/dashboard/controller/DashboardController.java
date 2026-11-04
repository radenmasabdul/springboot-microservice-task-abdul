package com.example.bookmanagement.dashboard.controller;

import com.example.bookmanagement.common.dto.ApiResponse;
import com.example.bookmanagement.common.util.ResponseHandler;
import com.example.bookmanagement.dashboard.dto.response.DashboardResponse;
import com.example.bookmanagement.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

  private final DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
    return ResponseHandler.ok(
      "Dashboard data retrieved successfully",
      dashboardService.getDashboard()
    );
  }
}
