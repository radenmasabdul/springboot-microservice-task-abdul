package com.example.bookmanagement.book.service;

import org.springframework.stereotype.Service;

@Service 
public class IsbnService {

  public String generateValidIsbn(String rawInput) {

    if (rawInput == null) {
      throw new IllegalArgumentException("Input cannot be empty");
    }

    String cleanInput = rawInput.replaceAll("[^0-9]", "");

    if (cleanInput.length() != 12) {
      throw new IllegalArgumentException("The input must be exactly 12 digits");
    }

    if (!cleanInput.startsWith("978") && !cleanInput.startsWith("979")) {
      throw new IllegalArgumentException("The input must start with 978 or 979");
    }

    String registrationGroup = cleanInput.substring(3, 6);

    if (!registrationGroup.equals("602") && !registrationGroup.equals("623")) {
      throw new IllegalArgumentException(
        "The registration group must be 602 or 623"
      );
    }

    int sum = 0;

    for (int i = 0; i < 12; i++) {
      int digit = Character.getNumericValue(cleanInput.charAt(i));
      sum += (i % 2 == 0) ? digit : digit * 3;
    }

    int checkDigit = (10 - (sum % 10)) % 10;

    String fullIsbn = cleanInput + checkDigit;

    return fullIsbn;
  }
}