package com.pulsar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.PostResponse;
import com.pulsar.service.PostSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String query) {
        List<PostResponse> posts = postSearchService.searchPosts(query);
        return ResponseEntity.ok(posts);
    }
}