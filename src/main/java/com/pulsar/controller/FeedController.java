package com.pulsar.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.PostResponse;
import com.pulsar.service.FeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getFeed(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body(List.of());
            }
            
            List<PostResponse> feed = feedService.getFeedForUser(principal.getName());
            return ResponseEntity.ok(feed);
            
        } catch (Exception e) {
            // Return empty list if there's any error
            return ResponseEntity.ok(List.of());
        }
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<List<PostResponse>> getFeedPaginated(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body(List.of());
            }
            
            // Get all feed posts
            List<PostResponse> allFeed = feedService.getFeedForUser(principal.getName());
            
            // Simple pagination logic
            int start = page * size;
            int end = Math.min(start + size, allFeed.size());
            
            if (start >= allFeed.size()) {
                return ResponseEntity.ok(List.of());
            }
            
            List<PostResponse> paginatedFeed = allFeed.subList(start, end);
            return ResponseEntity.ok(paginatedFeed);
            
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }
}