package com.capsule.capsule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capsule.capsule.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}