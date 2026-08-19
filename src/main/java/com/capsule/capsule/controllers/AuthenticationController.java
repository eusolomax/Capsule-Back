package com.capsule.capsule.controllers;

import com.capsule.capsule.services.AuthorizationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capsule.capsule.dtos.request.AuthenticationRequest;
import com.capsule.capsule.dtos.request.CreateUserRequest;
import com.capsule.capsule.dtos.response.LoginResponse;
import com.capsule.capsule.dtos.response.UserResponse;
import com.capsule.capsule.entities.User;
import com.capsule.capsule.infra.security.TokenService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthorizationService authorizationService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthorizationService authorizationService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequest request) {
        var usernamePassowrd = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassowrd);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authorizationService.createUser(request));
    }
}
