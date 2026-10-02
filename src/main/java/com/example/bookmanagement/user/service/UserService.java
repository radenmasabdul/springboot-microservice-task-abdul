package com.example.bookmanagement.user.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookmanagement.common.exception.AppException;
import com.example.bookmanagement.common.mapper.UserMapper;
import com.example.bookmanagement.common.specification.GenericSpecification;
import com.example.bookmanagement.common.util.RepositoryUtils;
import com.example.bookmanagement.user.dto.request.CreateUserRequest;
import com.example.bookmanagement.user.dto.request.UpdateUserRequest;
import com.example.bookmanagement.user.dto.response.UserResponse;
import com.example.bookmanagement.user.entity.Role;
import com.example.bookmanagement.user.entity.Status;
import com.example.bookmanagement.user.entity.User;
import com.example.bookmanagement.user.repository.UserRepository;

@Service 
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    UserMapper userMapper) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
      this.userMapper = userMapper;
  }

  public UserResponse createUser(CreateUserRequest request) {
    User user = userMapper.toEntity(request);

    user.setPassword(
      passwordEncoder.encode(request.getPassword())
    );
        
    User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser);
  }

  public Page<UserResponse> getAllUsers(String search, Role role, Status status, int page, int size) {
    List<String> columnToSearch = Arrays.asList("name", "email");

    Specification<User> spec = Specification.<User>where(GenericSpecification.searchByColumn(search, columnToSearch))
      .and(GenericSpecification.<User>equalsColumn("role", role))
      .and(GenericSpecification.<User>equalsColumn("status", status));

    PageRequest pageable = PageRequest.of(page, size);
    Page<User> userPage = userRepository.findAll(spec, pageable);

    return userPage.map(userMapper::toResponse);
  }

  public UserResponse getUserById(Long id) {
    User user = RepositoryUtils.findOrThrow(userRepository, id, "User");
    return userMapper.toResponse(user);
  }

  public UserResponse updateUser(Long id, UpdateUserRequest request) {

    User user = RepositoryUtils.findOrThrow(
      userRepository,
      id,
      "User"
    );

    userMapper.updateEntityFromRequest(request, user);

    if (request.getPassword() != null) {
      user.setPassword(
        passwordEncoder.encode(request.getPassword())
      );
    }

    User updatedUser = userRepository.save(user);

    return userMapper.toResponse(updatedUser);
  }

  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new AppException(HttpStatus.NOT_FOUND, "User with ID " + id + " was not found");
    }
    userRepository.deleteById(id);
  }
}
