package com.capsule.capsule.controllers;

import com.capsule.capsule.dtos.response.UserResponse;

import java.util.List;
import java.util.UUID;

import com.capsule.capsule.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capsule.capsule.services.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
   private final UserService userService;
   private final UserMapper mapper;

   public UserController(UserService userService, UserMapper mapper) {
      this.userService = userService;
      this.mapper = mapper;
   }

   @GetMapping("/all")
   public ResponseEntity<List<UserResponse>> listAll() {
      return ResponseEntity.ok(userService.listAll());
   }

   @GetMapping("/user/{UUID}")
   public ResponseEntity<UserResponse> listUserByUUID(@Valid @PathVariable("UUID") UUID UUID) {
      return ResponseEntity.ok(mapper.toResponse(userService.findByUuid(UUID)));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<UserResponse> create(@Valid @PathVariable("id") Long userId) {
      return ResponseEntity.ok(mapper.toResponse(userService.deleteUserByID(userId)));
   }
}