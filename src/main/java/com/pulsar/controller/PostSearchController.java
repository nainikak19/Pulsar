package com.pulsar.controller;

import com.pulsar.dto.PostResponse;
import com.pulsar.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @GetMapping("/tweets")
    public ResponseEntity<List<PostResponse>> searchTweets(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(postSearchService.searchPosts(keyword));
    }
}
