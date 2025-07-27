package com.pulsar.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MessageResponse> follow(@PathVariable String username, Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Authentication required"));
        }
        
        try {
            followService.followUser(principal.getName(), username);
            return ResponseEntity.ok(new MessageResponse("Followed user successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> unfollow(@PathVariable String username, Principal principal) {
        System.out.println("=== UNFOLLOW REQUEST START ===");
        System.out.println("Target username: " + username);
        System.out.println("Principal: " + (principal != null ? principal.getName() : "null"));
        
        if (principal == null || principal.getName() == null) {
            System.out.println("Authentication failed - returning 400");
            return ResponseEntity.badRequest().body(new MessageResponse("Authentication required"));
        }

        try {
            followService.unfollowUser(principal.getName(), username);
            System.out.println("=== UNFOLLOW SUCCESS ===");
            return ResponseEntity.ok(new MessageResponse("Unfollowed user successfully"));
        } catch (Exception e) {
            System.out.println("=== UNFOLLOW ERROR ===");
            System.out.println("Error type: " + e.getClass().getSimpleName());
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable String username) {
        List<Follow> followers = followService.getFollowers(username);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<Follow>> getFollowing(@PathVariable String username) {
        List<Follow> following = followService.getFollowing(username);
        return ResponseEntity.ok(following);
    }
}