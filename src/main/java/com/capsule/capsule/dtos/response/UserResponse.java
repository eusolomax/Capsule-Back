package com.capsule.capsule.dtos.response;

import java.util.List;
import java.util.UUID;

public record UserResponse(
    UUID uuid,
    String name,
    String email,
    List<TrackResponse> tracks
) {}
