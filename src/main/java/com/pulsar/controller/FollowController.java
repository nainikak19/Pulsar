package com.pulsar.controller;

import java.security.Principal;
import java.util.List;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{username}")
    public ResponseEntity<MessageResponse> followUser(@PathVariable String username, Principal principal) {
        log.info("Follow request: {} -> {}", principal != null ? principal.getName() : "null", username);
        
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body(new MessageResponse("Authentication required"));
        }
        
        try {
            followService.followUser(principal.getName(), username);
            return ResponseEntity.ok(new MessageResponse("Successfully followed " + username));
            
        } catch (RuntimeException e) {
            log.warn("Follow failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
            
        } catch (Exception e) {
            log.error("Unexpected error in follow: ", e);
            return ResponseEntity.status(500).body(new MessageResponse("An unexpected error occurred"));
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> unfollowUser(@PathVariable String username, Principal principal) {
        log.info("Unfollow request: {} -> {}", principal != null ? principal.getName() : "null", username);
        
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body(new MessageResponse("Authentication required"));
        }
        
        try {
            followService.unfollowUser(principal.getName(), username);
            return ResponseEntity.ok(new MessageResponse("Successfully unfollowed " + username));
            
        } catch (RuntimeException e) {
            log.warn("Unfollow failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
            
        } catch (Exception e) {
            log.error("Unexpected error in unfollow: ", e);
            return ResponseEntity.status(500).body(new MessageResponse("An unexpected error occurred"));
        }
    }

    @GetMapping("/status/{username}")
    public ResponseEntity<MessageResponse> getFollowStatus(@PathVariable String username, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(new MessageResponse("Authentication required"));
        }
        
        try {
            boolean isFollowing = followService.isFollowing(principal.getName(), username);
            return ResponseEntity.ok(new MessageResponse("Following " + username + ": " + isFollowing));
            
        } catch (Exception e) {
            log.error("Error getting follow status: ", e);
            return ResponseEntity.ok(new MessageResponse("Following " + username + ": false"));
        }
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<?> getFollowers(@PathVariable String username) {
        try {
            List<Follow> followers = followService.getFollowers(username);
            Long count = followService.getFollowersCount(username);
            return ResponseEntity.ok(new MessageResponse(username + " has " + count + " followers"));
            
        } catch (Exception e) {
            log.error("Error getting followers: ", e);
            return ResponseEntity.status(500).body(new MessageResponse("Error getting followers"));
        }
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<?> getFollowing(@PathVariable String username) {
        try {
            List<Follow> following = followService.getFollowing(username);
            Long count = followService.getFollowingCount(username);
            return ResponseEntity.ok(new MessageResponse(username + " is following " + count + " users"));
            
        } catch (Exception e) {
            log.error("Error getting following: ", e);
            return ResponseEntity.status(500).body(new MessageResponse("Error getting following"));
        }
    }

    @GetMapping("/counts/{username}")
    public ResponseEntity<MessageResponse> getFollowCounts(@PathVariable String username) {
        try {
            Long followersCount = followService.getFollowersCount(username);
            Long followingCount = followService.getFollowingCount(username);
            
            String message = String.format("%s: %d followers, %d following", 
                    username, followersCount, followingCount);
            
            return ResponseEntity.ok(new MessageResponse(message));
            
        } catch (Exception e) {
            log.error("Error getting follow counts: ", e);
            return ResponseEntity.status(500).body(new MessageResponse("Error getting follow counts"));
        }
    }
}