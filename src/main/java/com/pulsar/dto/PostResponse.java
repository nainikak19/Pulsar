package com.pulsar.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
}
