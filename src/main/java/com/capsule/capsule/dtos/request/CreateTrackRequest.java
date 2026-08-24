package com.capsule.capsule.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTrackRequest(
    @NotBlank
    String trackName,
    
    String trackDescription
    
    // TODO -- Let user create the REAL track
    // Add parameters like: track file, duration calc... (to finally not mock it all)
) {}
