package com.pulsar.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.MessageResponse;
import com.pulsar.model.Follow;
import com.pulsar.service.FollowService;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{username}")
    public ResponseEntity<MessageResponse> follow(@PathVariable String username, @AuthenticationPrincipal UserDetails user) {
        followService.followUser(user.getUsername(), username);
        return ResponseEntity.ok(new MessageResponse("Followed user successfully"));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> unfollow(@PathVariable String username, Principal principal) {
        followService.unfollowUser(principal.getName(), username);
        return ResponseEntity.ok(new MessageResponse("Unfollowed user successfully"));
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowers(username));
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<Follow>> getFollowing(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowing(username));
    }
}
