package com.capsule.capsule.controllers;

import com.capsule.capsule.entities.User;

import java.util.List;

import org.springframework.http.HttpStatus;
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
   public List<User> listAll() {
      return userService.listAll();
   }

   @PostMapping("/create")
   @ResponseStatus(HttpStatus.CREATED)
   public User create(@Valid @RequestBody User user) {
      return userService.createUser(user);
   }
   
   @DeleteMapping("/delete/{id}")
   @ResponseStatus(HttpStatus.OK)
   public User create(@Valid @PathVariable("id") Long userId) {
      return userService.deleteUserByID(userId);
   }
}