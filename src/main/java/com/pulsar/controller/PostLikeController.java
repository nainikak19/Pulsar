package com.pulsar.controller;

import com.pulsar.dto.LikePostRequest;
import com.pulsar.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<String> likeOrUnlikePost(@RequestBody LikePostRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        postLikeService.likeOrUnlikePost(request.getPostId(), userDetails.getUsername());
        return ResponseEntity.ok("Post like status toggled successfully.");
    }
}
