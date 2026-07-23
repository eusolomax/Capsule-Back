package com.capsule.capsule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capsule.capsule.entities.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
   List<Track> findByUserId(Long userId);
}