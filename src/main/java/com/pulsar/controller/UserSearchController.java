package com.pulsar.controller;

import com.pulsar.dto.UserSearchResponse;
import com.pulsar.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class UserSearchController {

    private final UserSearchService userSearchService;

    @GetMapping
    public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userSearchService.searchUsers(query));
    }
}
