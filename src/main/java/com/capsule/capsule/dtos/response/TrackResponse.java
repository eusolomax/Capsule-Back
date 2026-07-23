package com.capsule.capsule.dtos.response;

import java.util.UUID;

public record TrackResponse(
    UUID uuid,
    String trackName,
    String trackDescription,
    Integer durationSeconds,
    String filePath
) {}
