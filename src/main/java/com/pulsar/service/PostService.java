package com.pulsar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsar.dto.PostResponse;
import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public PostResponse updatePost(Long postId, String content, String usernameOrEmail) {
        // Input validation
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty");
        }
        
        if (content.trim().length() > 280) {
            throw new IllegalArgumentException("Post content cannot exceed 280 characters");
        }
        
        // Find the post
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        
        // Find the current user
        User currentUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));
        
        // Check if current user is the owner of the post
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only update your own posts");
        }
        
        // Update the post
        post.setContent(content.trim());
        
        Post savedPost = postRepository.save(post);
        
        // Return the updated post as PostResponse
        return PostResponse.builder()
            .id(savedPost.getId())
            .content(savedPost.getContent())
            .username(savedPost.getUser().getUsername())
            .createdAt(savedPost.getCreatedAt())
            .likeCount(savedPost.getLikeCount())
            .likedByCurrentUser(savedPost.isLikedByUser(currentUser))
            .build();
    }
    
    @Transactional
    public void deletePost(Long postId, String usernameOrEmail) {
        // Find the post
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        
        // Find the current user
        User currentUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));
        
        // Check if current user is the owner of the post
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only delete your own posts");
        }
        
        // Delete the post
        postRepository.delete(post);
    }
}