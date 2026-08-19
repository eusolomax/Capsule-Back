package com.capsule.capsule.entities;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tracks")
public class Track {

   public Track() {
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, unique = true)
   private UUID uuid = UUID.randomUUID();

   @NotBlank(message = "Track name cannot be empty")
   @Column(name = "track_name", nullable = false)
   private String trackName;

   @Column(name = "track_description")
   private String trackDescription;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private User user;

   @Column(name = "duration_seconds")
   private Integer durationSeconds;

   @Column(name = "file_path")
   private String filePath;
}