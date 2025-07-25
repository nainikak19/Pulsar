package com.pulsar.service;

import com.pulsar.dto.PostResponse;
import java.util.List;

public interface PostService {
    void createPost(String username, String content);
    List<PostResponse> getUserPosts(String username);
    List<PostResponse> getAllPosts();
    void deletePost(String username, Long postId);
}
