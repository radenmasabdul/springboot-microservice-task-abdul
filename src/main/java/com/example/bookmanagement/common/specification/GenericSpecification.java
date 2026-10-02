package com.example.bookmanagement.common.specification;

import jakarta.persistence.criteria.Predicate;
import java.util.*;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification {
  
  public static <T> Specification<T> searchByColumn(String keyword, List<String> columns) {
    return (root, query, criteriaBuilder) -> {
      if (keyword == null || keyword.trim().isEmpty() || columns == null || columns.isEmpty()) {
        return criteriaBuilder.conjunction();
      }

      String likeKeyword = "%" + keyword.toLowerCase() + "%";
      List<Predicate> predicates = new ArrayList<>();

      for (String col : columns) {
        Predicate predicate = criteriaBuilder.like(
          criteriaBuilder.lower(root.get(col).as(String.class)),
          likeKeyword);

        predicates.add(predicate);
      }

      return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    };
  }

  public static <T> Specification<T> equalsColumn(String column, Object value) {
    return (root, query, criteriaBuilder) -> {
      if (value == null) {
        return criteriaBuilder.conjunction();
      }

      return criteriaBuilder.equal(root.get(column), value);
    };
  }

  public static <T> Specification<T> inColumn(String column, List<?> values) {
    return (root, query, criteriaBuilder) -> {
      if (values == null || values.isEmpty()) {
        return criteriaBuilder.conjunction();
      }

      return root.get(column).in(values);
    };
  }
}
