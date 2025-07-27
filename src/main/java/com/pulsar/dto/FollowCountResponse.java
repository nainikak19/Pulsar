package com.pulsar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowCountResponse {
    private long followersCount;
    private long followingCount;
}