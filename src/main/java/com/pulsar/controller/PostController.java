package com.pulsar.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.MessageResponse;
import com.pulsar.dto.PostResponse;
import com.pulsar.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("api/posts")
    public ResponseEntity<MessageResponse> createPost(@RequestParam String content, Principal principal) {
        postService.createPost(principal.getName(), content);
        return ResponseEntity.ok(new MessageResponse("Post created successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostResponse>> getMyPosts(Principal principal) {
        return ResponseEntity.ok(postService.getUserPosts(principal.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable Long postId, Principal principal) {
        postService.deletePost(principal.getName(), postId);
        return ResponseEntity.ok(new MessageResponse("Post deleted successfully"));
    }
}
