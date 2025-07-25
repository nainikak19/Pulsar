package com.pulsar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pulsar.dto.PostResponse;
import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponse> getFeedForUser(String usernameOrEmail) {
        // Find the user by username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get list of users the current user is following
        List<User> following = user.getFollowing();

        // Also include the current user's own posts in the feed
        following.add(user);

        // Fetch posts by all followed users (including own)
        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(following);

        // Map posts to PostResponse DTO
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .username(post.getUser().getUsername())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
