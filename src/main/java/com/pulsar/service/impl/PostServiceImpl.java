// PostServiceImpl.java

package com.pulsar.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pulsar.dto.PostResponse;
import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;
import com.pulsar.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void createPost(String username, String content) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = Post.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        postRepository.save(post);
    }

    @Override
    public List<PostResponse> getUserPosts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return postRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(String username, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this post");
        }

        postRepository.deleteById(postId);
    }
}
