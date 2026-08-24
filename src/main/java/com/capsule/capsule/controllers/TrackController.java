package com.capsule.capsule.controllers;

import com.capsule.capsule.dtos.request.CreateTrackRequest;
import com.capsule.capsule.dtos.response.TrackResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.capsule.capsule.services.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/track")
public class TrackController {
   private final TrackService trackService;

   public TrackController(TrackService trackService) {
      this.trackService = trackService;
   }

   @GetMapping("/all")
   public ResponseEntity<List<TrackResponse>> listAll(Authentication authentication) {
      return ResponseEntity.ok(trackService.listAllUserTracks(authentication.getPrincipal().toString()));
   }

   @PostMapping("/create")
   public ResponseEntity<TrackResponse> create(@Valid @RequestBody CreateTrackRequest track, Authentication authentication) {
      return ResponseEntity.ok(trackService.createTrack(track, authentication.getPrincipal().toString()));
   }
   
   @DeleteMapping("/delete/{uuid}")
   public ResponseEntity<TrackResponse> delete(@Valid @PathVariable("uuid") UUID trackUUID) {
      return ResponseEntity.ok(trackService.deleteTrack(trackUUID));
   }
}