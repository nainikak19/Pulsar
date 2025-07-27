package com.pulsar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulsar.model.Follow;
import com.pulsar.model.User;
import com.pulsar.repository.FollowRepository;
import com.pulsar.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void unfollowUser(String followerEmail, String targetUsername) {
        // Find follower by EMAIL (since that's what's in the JWT)
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> new RuntimeException("Follower not found with email: " + followerEmail));
        
        // Find target by USERNAME (since that's what's in the URL)
        User target = userRepository.findByUsername(targetUsername)
            .orElseThrow(() -> new RuntimeException("User to unfollow not found: " + targetUsername));

        boolean followExists = followRepository.existsByFollowerAndFollowing(follower, target);
        if (!followExists) {
            throw new RuntimeException("You are not following user: " + targetUsername);
        }

        followRepository.deleteByFollowerAndFollowing(follower, target);
    }

    @Transactional
    public void followUser(String followerEmail, String targetUsername) {
        // Find follower by EMAIL
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> new RuntimeException("Follower not found with email: " + followerEmail));
        
        // Find target by USERNAME
        User target = userRepository.findByUsername(targetUsername)
            .orElseThrow(() -> new RuntimeException("User to follow not found: " + targetUsername));

        if (followRepository.existsByFollowerAndFollowing(follower, target)) {
            throw new RuntimeException("Already following this user");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(target);
        followRepository.save(follow);
    }

    public List<Follow> getFollowers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return followRepository.findByFollowing(user);
    }

    public List<Follow> getFollowing(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return followRepository.findByFollower(user);
    }
}