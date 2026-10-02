package com.example.bookmanagement.common.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.bookmanagement.user.dto.request.CreateUserRequest;
import com.example.bookmanagement.user.dto.request.UpdateUserRequest;
import com.example.bookmanagement.user.dto.response.UserResponse;
import com.example.bookmanagement.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User toEntity(CreateUserRequest request);

  UserResponse toResponse(User user);

  List<UserResponse> toResponseList(List<User> users);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "name", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateEntityFromRequest(
    UpdateUserRequest request,
    @MappingTarget User user
  );
}