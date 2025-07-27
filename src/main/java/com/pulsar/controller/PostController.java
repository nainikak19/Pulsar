package com.pulsar.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pulsar.dto.PostResponse;
import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /** Create Post */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestParam("content") String content, Principal principal) {
        User user = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return ResponseEntity.ok(PostResponse.builder()
                .id(savedPost.getId())
                .content(savedPost.getContent())
                .username(savedPost.getUser().getUsername())
                .createdAt(savedPost.getCreatedAt())
                .likeCount(0)
                .likedByCurrentUser(false)
                .build());
    }

    /** Get all posts */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(Principal principal) {
        User currentUser = null;
        if (principal != null) {
            currentUser = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                    .orElse(null);
        }

        // Use basic findAll and sort in Java
        List<Post> allPosts = postRepository.findAll();
        List<Post> sortedPosts = allPosts.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());

        final User userForLambda = currentUser;

        List<PostResponse> responses = sortedPosts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .username(post.getUser().getUsername())
                        .createdAt(post.getCreatedAt())
                        .likeCount(0)
                        .likedByCurrentUser(false)
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /** Get my posts */
    @GetMapping("/me")
    public ResponseEntity<List<PostResponse>> getMyPosts(Principal principal) {
        User currentUser = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Use basic findAll and filter by user
        List<Post> allPosts = postRepository.findAll();
        List<Post> myPosts = allPosts.stream()
                .filter(post -> post.getUser().getId().equals(currentUser.getId()))
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());

        List<PostResponse> responses = myPosts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .username(post.getUser().getUsername())
                        .createdAt(post.getCreatedAt())
                        .likeCount(0)
                        .likedByCurrentUser(true)
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /** Update post */
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @RequestParam("content") String content,
            Principal principal) {
        try {
            if (content == null || content.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Content cannot be empty");
            }

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            User currentUser = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!post.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(403).body("You can only update your own posts");
            }

            post.setContent(content.trim());
            Post savedPost = postRepository.save(post);

            PostResponse response = PostResponse.builder()
                    .id(savedPost.getId())
                    .content(savedPost.getContent())
                    .username(savedPost.getUser().getUsername())
                    .createdAt(savedPost.getCreatedAt())
                    .likeCount(0)
                    .likedByCurrentUser(true)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error updating post: " + e.getMessage());
        }
    }

    /** Delete post */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, Principal principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User currentUser = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).body("You can only delete your own posts");
        }

        postRepository.delete(post);
        return ResponseEntity.ok("Post deleted successfully");
    }

    /** Get single post */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId, Principal principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User currentUser = null;
        if (principal != null) {
            currentUser = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                    .orElse(null);
        }

        boolean isLikedByCurrentUser = false;
        if (currentUser != null && post.getUser().getId().equals(currentUser.getId())) {
            isLikedByCurrentUser = true;
        }

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .likeCount(0)
                .likedByCurrentUser(isLikedByCurrentUser)
                .build();

        return ResponseEntity.ok(response);
    }
}