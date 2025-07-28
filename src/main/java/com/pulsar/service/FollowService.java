package com.pulsar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsar.model.Follow;
import com.pulsar.model.User;
import com.pulsar.repository.FollowRepository;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FollowService {
    
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void followUser(String followerEmail, String targetUsername) {
        log.info("User {} attempting to follow {}", followerEmail, targetUsername);
        
        try {
            // Find follower by email (JWT contains email)
            User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found with email: " + followerEmail));
            
            // Find target by username (URL parameter)
            User target = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User to follow not found: " + targetUsername));
            
            // Check if already following
            if (followRepository.existsByFollowerAndFollowing(follower, target)) {
                throw new RuntimeException("Already following this user");
            }
            
            // Can't follow yourself
            if (follower.getId().equals(target.getId())) {
                throw new RuntimeException("You cannot follow yourself");
            }
            
            // Create follow relationship
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(target);
            followRepository.save(follow);
            
            log.info("Successfully created follow relationship: {} -> {}", follower.getUsername(), target.getUsername());
            
        } catch (Exception e) {
            log.error("Error in followUser: {}", e.getMessage());
            throw e;
        }
    }

    public void unfollowUser(String followerEmail, String targetUsername) {
        log.info("User {} attempting to unfollow {}", followerEmail, targetUsername);
        
        try {
            // Find follower by email
            User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new RuntimeException("Follower not found with email: " + followerEmail));
            
            // Find target by username
            User target = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found: " + targetUsername));
            
            // Check if following relationship exists
            if (!followRepository.existsByFollowerAndFollowing(follower, target)) {
                throw new RuntimeException("You are not following user: " + targetUsername);
            }
            
            // Delete follow relationship
            followRepository.deleteByFollowerAndFollowing(follower, target);
            
            log.info("Successfully removed follow relationship: {} -> {}", follower.getUsername(), target.getUsername());
            
        } catch (Exception e) {
            log.error("Error in unfollowUser: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowers(String username) {
        log.info("Getting followers for user: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        return followRepository.findByFollowing(user);
    }

    @Transactional(readOnly = true)
    public List<Follow> getFollowing(String username) {
        log.info("Getting following for user: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        return followRepository.findByFollower(user);
    }

    @Transactional(readOnly = true)
    public boolean isFollowing(String followerEmail, String targetUsername) {
        try {
            User follower = userRepository.findByEmail(followerEmail).orElse(null);
            User target = userRepository.findByUsername(targetUsername).orElse(null);
            
            if (follower == null || target == null) {
                return false;
            }
            
            return followRepository.existsByFollowerAndFollowing(follower, target);
            
        } catch (Exception e) {
            log.error("Error checking follow status: {}", e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Long getFollowersCount(String username) {
        try {
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) return 0L;
            
            return followRepository.countFollowers(user);
            
        } catch (Exception e) {
            log.error("Error getting followers count: {}", e.getMessage());
            return 0L;
        }
    }

    @Transactional(readOnly = true)
    public Long getFollowingCount(String username) {
        try {
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) return 0L;
            
            return followRepository.countFollowing(user);
            
        } catch (Exception e) {
            log.error("Error getting following count: {}", e.getMessage());
            return 0L;
        }
    }
}