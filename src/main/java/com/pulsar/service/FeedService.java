package com.pulsar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pulsar.dto.PostResponse;
import com.pulsar.exception.UserNotFoundException;
import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponse> getFeedForUser(String usernameOrEmail) {
        log.info("Getting feed for user: {}", usernameOrEmail);
        
        try {
            // Find the user by username or email
            User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new UserNotFoundException("User not found: " + usernameOrEmail));

            // Get list of users the current user is following
            List<User> following = new ArrayList<>();
            
            // Check if user has following relationship
            if (user.getFollowing() != null) {
                following.addAll(user.getFollowing());
            }
            
            // Also include the current user's own posts in the feed
            following.add(user);

            // Get all posts and filter by followed users
            List<Post> allPosts = postRepository.findAll();
            List<Post> posts = allPosts.stream()
                    .filter(post -> following.contains(post.getUser()))
                    .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                    .collect(Collectors.toList());

            // Map posts to PostResponse DTO
            return posts.stream()
                    .map(post -> mapToResponse(post, user))
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            log.error("Error getting feed for user: {}", usernameOrEmail, e);
            // Return empty list if there's any error
            return new ArrayList<>();
        }
    }
    
    private PostResponse mapToResponse(Post post, User currentUser) {
        try {
            // Use the built-in methods from your Post model
            int likeCount = post.getLikeCount();
            boolean likedByCurrentUser = post.isLikedByUser(currentUser);
            
            return PostResponse.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .username(post.getUser().getUsername())
                    .likeCount(likeCount)
                    .likedByCurrentUser(likedByCurrentUser)
                    .build();
                    
        } catch (Exception e) {
            log.error("Error mapping post to response", e);
            // Fallback response
            return PostResponse.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .username("User")
                    .likeCount(0)
                    .likedByCurrentUser(false)
                    .build();
        }
    }
}