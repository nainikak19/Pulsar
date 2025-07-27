package com.pulsar.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pulsar.dto.UserSearchResponse;
import com.pulsar.model.User;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    public List<UserSearchResponse> searchUsers(String query) {
        // Input validation
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        if (query.trim().length() < 2) {
            return Collections.emptyList();
        }

        List<User> users = userRepository
            .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                query.trim(), query.trim());

        return users.stream()
            .limit(20) // Limit results to prevent overwhelming response
            .map(user -> new UserSearchResponse(user.getUsername(), user.getEmail()))
            .collect(Collectors.toList());
    }
    
    // Method with limit parameter
    public List<UserSearchResponse> searchUsers(String query, int limit) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        if (query.trim().length() < 2) {
            return Collections.emptyList();
        }

        List<User> users = userRepository
            .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                query.trim(), query.trim());

        return users.stream()
            .limit(Math.min(limit, 50)) // Cap at 50 to prevent abuse
            .map(user -> new UserSearchResponse(user.getUsername(), user.getEmail()))
            .collect(Collectors.toList());
    }
}
