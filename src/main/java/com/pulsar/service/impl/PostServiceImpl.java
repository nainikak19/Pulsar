package com.pulsar.service.impl;

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
public class PostServiceImpl {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public PostResponse updatePost(Long postId, String content, String usernameOrEmail) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be empty");
        }
        
        if (content.trim().length() > 280) {
            throw new IllegalArgumentException("Post content cannot exceed 280 characters");
        }
        
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        
        User currentUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));
        
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only update your own posts");
        }
        
        post.setContent(content.trim());
        Post savedPost = postRepository.save(post);
        
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
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        
        User currentUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));
        
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only delete your own posts");
        }
        
        postRepository.delete(post);
    }
}