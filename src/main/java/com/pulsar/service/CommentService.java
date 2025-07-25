package com.pulsar.service;

import com.pulsar.dto.CommentRequest;
import com.pulsar.dto.CommentResponse;
import com.pulsar.model.Comment;
import com.pulsar.model.Post;
import com.pulsar.model.User;
import com.pulsar.repository.CommentRepository;
import com.pulsar.repository.PostRepository;
import com.pulsar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void addComment(String username, CommentRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Post> postOpt = postRepository.findById(request.getPostId());

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            throw new RuntimeException("User or Post not found");
        }

        Comment comment = Comment.builder()
                .user(userOpt.get())
                .post(postOpt.get())
                .content(request.getContent())
                .build();

        commentRepository.save(comment);
    }

    public List<CommentResponse> getCommentsForPost(Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        List<Comment> comments = commentRepository.findByPostOrderByCreatedAtDesc(postOpt.get());

        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
