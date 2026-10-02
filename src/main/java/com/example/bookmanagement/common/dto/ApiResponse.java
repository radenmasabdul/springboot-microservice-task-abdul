package com.example.bookmanagement.common.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private int status;
  private String message;
  private T data;
  private Integer page;
  private Integer size;
  private Long totalElements;
  private Integer totalPages;
  private LocalDateTime timestamp;
}
