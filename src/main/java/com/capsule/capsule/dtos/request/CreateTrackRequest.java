package com.capsule.capsule.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTrackRequest(
    @NotBlank
    String trackName,
    
    String trackDescription
) {}
