package com.capsule.capsule.dtos.response;

import com.capsule.capsule.enums.AudioFormat;
import com.capsule.capsule.enums.ProcessingStatus;

import java.util.UUID;

public record TrackResponse(
        UUID uuid,
        String trackName,
        String trackDescription,
        Integer durationSeconds,
        String filePath,
        TrackResponse convertedFrom,
        AudioFormat format,
        ProcessingStatus status
) {
}
