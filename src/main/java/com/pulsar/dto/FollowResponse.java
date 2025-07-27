package com.pulsar.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowResponse {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime followedAt;
}