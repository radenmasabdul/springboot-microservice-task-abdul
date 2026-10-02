package com.example.bookmanagement.common.util;

import com.example.bookmanagement.common.exception.AppException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public class RepositoryUtils {

  private RepositoryUtils() {
  }

  public static <T, ID> T findOrThrow(JpaRepository<T, ID> repository, ID id, String entityName) {
    return repository.findById(id)
      .orElseThrow(() -> new AppException(
        HttpStatus.NOT_FOUND,
        entityName + " with ID " + id + " was not found"
      )
    );
  }
}
