package com.example.bookmanagement.middleware.service;

import com.example.bookmanagement.middleware.dto.ExternalPostResponse;
import com.example.bookmanagement.middleware.exception.ExternalApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class JsonPlaceholderService {
  private static final Logger log = LoggerFactory.getLogger(JsonPlaceholderService.class);

  private final RestClient restClient;

  public JsonPlaceholderService(RestClient jsonPlaceholderRestClient) {
    this.restClient = jsonPlaceholderRestClient;
  }

  public List<ExternalPostResponse> getPosts() {
    log.info("Calling external API: GET /posts");
    List<ExternalPostResponse> posts = restClient.get()
      .uri("/posts")
      .retrieve()
      .onStatus(status -> status.isError(), (request, response) -> {
        log.error(
          "JSONPlaceholder returned HTTP {} for GET /posts",
          response.getStatusCode()
        );

        throw new ExternalApiException(
          "JSONPlaceholder returned HTTP "
            + response.getStatusCode().value()
        );
      })
      .body(
        org.springframework.core.ParameterizedTypeReference
          .forType(
            new org.springframework.core.ParameterizedTypeReference<
              List<ExternalPostResponse>>() {}.getType()
          )
      );
    log.info("External API returned {} posts", posts == null ? 0 : posts.size());
    if (posts != null && !posts.isEmpty()) {
      log.debug("First mapped post: {}", posts.get(0));
    }
    return posts;
  }
}
