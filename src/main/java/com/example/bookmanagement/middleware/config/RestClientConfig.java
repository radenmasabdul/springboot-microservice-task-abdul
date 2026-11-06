package com.example.bookmanagement.middleware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RestClientConfig {
  private static final Logger log = LoggerFactory.getLogger(RestClientConfig.class);

  @Bean
  public RestClient jsonPlaceholderRestClient() {
    log.info("Configuring RestClient for JSONPlaceholder: https://jsonplaceholder.typicode.com");
    return RestClient.builder()
      .baseUrl("https://jsonplaceholder.typicode.com")
      .build();
  }
}
