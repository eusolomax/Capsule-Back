package com.capsule.capsule.controllers;

import com.capsule.capsule.entities.Track;

import java.util.List;

import org.springframework.http.HttpStatus;
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

   @GetMapping("/all/{id}")
   public List<Track> listAll(@PathVariable("id") Long userId) {
      return trackService.listAllUserTracks(userId);
   }

   @PostMapping("/create")
   @ResponseStatus(HttpStatus.CREATED)
   public Track create(@Valid @RequestBody Track track) {
      return trackService.createTrack(track);
   }
   
   // @DeleteMapping("/delete/{id}")
   // @ResponseStatus(HttpStatus.OK)
   // public User create(@Valid @PathVariable("id") Long userId) {
   //    return userService.deleteUserByID(userId);
   // }
}