package com.pulsar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void likeOrUnlikePost(Long postId, String usernameOrEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + usernameOrEmail));

        // Toggle like
        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user); // Unlike
        } else {
            post.getLikedBy().add(user); // Like
        }

        postRepository.save(post);
    }

    // PUBLIC: You can pass username/email to check if that user liked the post
    public boolean isPostLikedByUser(Long postId, String usernameOrEmail) {
        if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
            return false; // No user specified
        }
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        return postOpt.isPresent() && userOpt.isPresent() && postOpt.get().getLikedBy().contains(userOpt.get());
    }

    // PUBLIC: Anyone can see like count
    public int getLikeCount(Long postId) {
        return postRepository.findById(postId).map(post -> post.getLikedBy().size()).orElse(0);
    }
}
