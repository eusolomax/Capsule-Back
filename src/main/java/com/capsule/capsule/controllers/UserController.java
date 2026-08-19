package com.capsule.capsule.controllers;

import com.capsule.capsule.dtos.response.UserResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capsule.capsule.services.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/all")
   public ResponseEntity<List<UserResponse>> listAll() {
      return ResponseEntity.ok(userService.listAll());
   }
   
   @DeleteMapping("/delete/{id}")
   public ResponseEntity<UserResponse> create(@Valid @PathVariable("id") Long userId) {
      return ResponseEntity.ok(userService.deleteUserByID(userId));
   }
}