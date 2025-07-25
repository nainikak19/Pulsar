package com.pulsar.controller;

import com.pulsar.dto.CommentRequest;
import com.pulsar.dto.CommentResponse;
import com.pulsar.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> commentOnPost(@AuthenticationPrincipal Principal principal,
                                           @RequestBody CommentRequest request) {
        commentService.addComment(principal.getName(), request);
        return ResponseEntity.ok("Comment added");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }
}
