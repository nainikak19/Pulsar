package com.pulsar.service;

import com.pulsar.dto.UserSearchResponse;
import com.pulsar.model.User;
import com.pulsar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    public List<UserSearchResponse> searchUsers(String query) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);

        return users.stream()
                .map(user -> new UserSearchResponse(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
