package com.pulsar.service;

import com.pulsar.dto.PostResponse;
import com.pulsar.model.Post;
import com.pulsar.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;

    public List<PostResponse> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByContentContainingIgnoreCaseOrderByCreatedAtDesc(keyword);
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
