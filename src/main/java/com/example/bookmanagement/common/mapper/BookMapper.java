package com.example.bookmanagement.common.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.bookmanagement.book.dto.request.CreateBookRequest;
import com.example.bookmanagement.book.dto.request.PatchBookRequest;
import com.example.bookmanagement.book.dto.request.UpdateBookRequest;
import com.example.bookmanagement.book.dto.response.BookResponse;
import com.example.bookmanagement.book.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Book toEntity(CreateBookRequest request);

  @Mapping(target = "createdBy", source = "createdBy.id")
  @Mapping(target = "updatedBy", source = "updatedBy.id")
  BookResponse toResponse(Book book);

  List<BookResponse> toResponseList(List<Book> books);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateEntityFromRequest(
    UpdateBookRequest request,
    @MappingTarget Book book
  );

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void patchEntityFromRequest(
    PatchBookRequest request,
    @MappingTarget Book book
  );
}