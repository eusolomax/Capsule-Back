package com.capsule.capsule.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.capsule.capsule.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String email);

   Optional<User> findByUuid(UUID uuid);
}