package com.pulsar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pulsar.dto.PostResponse;
import com.pulsar.model.Post;
import com.pulsar.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;

    public List<PostResponse> searchPosts(String query) {
        List<Post> posts = postRepository.findByContentContainingIgnoreCase(query);
        
        return posts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .likeCount(0) // You can calculate this later
                .likedByCurrentUser(false) // You can calculate this later
                .build();
    }
}