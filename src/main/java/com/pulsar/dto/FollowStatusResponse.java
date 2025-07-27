package com.pulsar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowStatusResponse {
    private boolean isFollowing;      // Current user follows the target user
    private boolean isFollowedBy;     // Target user follows the current user
    private boolean isMutualFollow;   // Both users follow each other
}