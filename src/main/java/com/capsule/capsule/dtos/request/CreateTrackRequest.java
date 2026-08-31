package com.capsule.capsule.dtos.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public record CreateTrackRequest(
        @NotBlank
        String trackName,

        String trackDescription,

        @NotBlank
        MultipartFile file
) {
}
