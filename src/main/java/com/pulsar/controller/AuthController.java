package com.pulsar.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.RegisterRequest;
import com.pulsar.model.User;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Check if username already exists
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            // Check if email already exists
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // Create new user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("ROLE_USER");

            userRepository.save(user);
            
            return ResponseEntity.ok("User registered successfully");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract username and password from Basic Auth header
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":", 2);
            String username = parts[0];
            String password = parts[1];

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            return ResponseEntity.ok("Login successful");
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/logged-in/user")
    public ResponseEntity<?> getLoggedInUser(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Not authenticated");
            }

            User user = userRepository.findByUsernameOrEmail(principal.getName(), principal.getName())
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            return ResponseEntity.ok("User: " + user.getUsername() + ", Email: " + user.getEmail());
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting user: " + e.getMessage());
        }
    }
}