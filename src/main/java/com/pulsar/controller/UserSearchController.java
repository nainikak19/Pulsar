package com.pulsar.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.UserSearchResponse;
import com.pulsar.service.UserSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search/users")  
@RequiredArgsConstructor
public class UserSearchController {

    private final UserSearchService userSearchService;

    @GetMapping
    public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userSearchService.searchUsers(query));
    }
    
}
