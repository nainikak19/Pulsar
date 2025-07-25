package com.pulsar.service;

import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void likeOrUnlikePost(Long postId, String username) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (postOpt.isEmpty() || userOpt.isEmpty()) {
            throw new RuntimeException("Post or User not found");
        }

        Post post = postOpt.get();
        User user = userOpt.get();

        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user); // Unlike
        } else {
            post.getLikedBy().add(user);    // Like
        }

        postRepository.save(post);
    }
}
