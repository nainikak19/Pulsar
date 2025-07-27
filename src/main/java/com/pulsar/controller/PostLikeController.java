package com.pulsar.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.service.PostLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
@Slf4j
public class PostLikeController {

    private final PostLikeService postLikeService;

    // Like or unlike (must be logged in)
    @PostMapping
    public ResponseEntity<String> likeOrUnlikePost(@RequestParam Long postId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("You must be logged in to like posts");
        }
        postLikeService.likeOrUnlikePost(postId, principal.getName());
        return ResponseEntity.ok("Post like status toggled successfully.");
    }

    // Public - anyone can check if current user liked post
    @GetMapping("/status/{postId}")
    public ResponseEntity<?> isPostLiked(@PathVariable Long postId, Principal principal) {
        boolean isLiked = principal != null && postLikeService.isPostLikedByUser(postId, principal.getName());
        return ResponseEntity.ok(isLiked);
    }

    // Public - anyone can see like count
    @GetMapping("/count/{postId}")
    public ResponseEntity<?> getLikeCount(@PathVariable Long postId) {
        int count = postLikeService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }
}
