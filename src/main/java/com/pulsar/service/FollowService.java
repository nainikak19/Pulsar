package com.pulsar.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pulsar.model.Follow;
import com.pulsar.model.User;
import com.pulsar.repository.FollowRepository;
import com.pulsar.repository.UserRepository;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public void followUser(String followerUsername, String targetUsername) {
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User target = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (!followRepository.existsByFollowerAndFollowing(follower, target)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(target);
            followRepository.save(follow);
        }
    }

    public void unfollowUser(String followerUsername, String targetUsername) {
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User target = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        followRepository.deleteByFollowerAndFollowing(follower, target);
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
