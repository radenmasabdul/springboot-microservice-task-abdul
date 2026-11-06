package com.example.bookmanagement.middleware.controller;

import com.example.bookmanagement.middleware.dto.ExternalPostResponse;
import com.example.bookmanagement.middleware.service.JsonPlaceholderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/middleware")
public class MiddlewareController {
  private static final Logger log = LoggerFactory.getLogger(MiddlewareController.class);

  private final JsonPlaceholderService jsonPlaceholderService;

  public MiddlewareController(JsonPlaceholderService jsonPlaceholderService) {
    this.jsonPlaceholderService = jsonPlaceholderService;
  }

  @GetMapping("/posts")
  public List<ExternalPostResponse> getPosts() {
    log.info("Incoming request: GET /api/middleware/posts");
    List<ExternalPostResponse> posts = jsonPlaceholderService.getPosts();
    log.info("Returning {} posts from middleware endpoint", posts == null ? 0 : posts.size());
    return posts;
  }

}
