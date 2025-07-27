package com.pulsar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String content;
    private String username;
    private LocalDateTime createdAt; 
}
