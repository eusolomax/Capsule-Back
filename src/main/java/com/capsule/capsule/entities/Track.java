package com.capsule.capsule.entities;

import java.util.UUID;

import com.capsule.capsule.enums.AudioFormat;
import com.capsule.capsule.enums.ProcessingStatus;
import jakarta.persistence.*;
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

   @Column(name = "track_name", nullable = false)
   private String trackName;

   @Column(name = "track_description")
   private String trackDescription;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @Column(name = "duration_seconds", nullable = false)
   private Integer durationSeconds;

   @Column(name = "file_path", nullable = false, unique = true)
   private String filePath;

   @Enumerated(EnumType.STRING)
   @Column(name = "status", nullable = false)
   private ProcessingStatus status;

   @Enumerated(EnumType.STRING)
   @Column(name = "format", nullable = false)
   private AudioFormat format;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "converted_from")
   private Track convertedFrom;
}