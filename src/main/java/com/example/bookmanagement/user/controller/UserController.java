package com.example.bookmanagement.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmanagement.common.dto.ApiResponse;
import com.example.bookmanagement.common.util.ResponseHandler;
import com.example.bookmanagement.user.dto.request.CreateUserRequest;
import com.example.bookmanagement.user.dto.request.UpdateUserRequest;
import com.example.bookmanagement.user.dto.response.UserResponse;
import com.example.bookmanagement.user.entity.Role;
import com.example.bookmanagement.user.entity.Status;
import com.example.bookmanagement.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(
    @RequestParam(required = false) String search,
    @RequestParam(required = false) Role role,
    @RequestParam(required = false) Status status,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {

    return ResponseHandler.okPage(
      "Users retrieved successfully",
      userService.getAllUsers(
        search,
        role,
        status,
        page,
        size
      )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<UserResponse>> getUserById(
    @PathVariable Long id) {

    return ResponseHandler.ok(
      "User details found",
      userService.getUserById(id)
    );
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<UserResponse>> createUser(
    @Valid @RequestBody CreateUserRequest request) {

    return ResponseHandler.created(
      "New user successfully added",
      userService.createUser(request)
    );
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<UserResponse>> updateUser(
    @PathVariable Long id,
    @Valid @RequestBody UpdateUserRequest request) {

    return ResponseHandler.ok(
      "User data successfully updated",
      userService.updateUser(id, request)
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMINISTRATOR')")
  public ResponseEntity<ApiResponse<Void>> deleteUser(
    @PathVariable Long id) {

    userService.deleteUser(id);

    return ResponseHandler.ok(
      "User deleted successfully",
      null
    );
  }
}