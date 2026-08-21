package com.capsule.capsule.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capsule.capsule.entities.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
   List<Track> findByUserId(Long userId);
   
   List<Track> findByUserUuid(UUID userUUID);

   Optional<Track> findByUuid(UUID uuid);
}