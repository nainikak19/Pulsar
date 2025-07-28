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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        log.info("Registration attempt for username: {}, email: {}", request.getUsername(), request.getEmail());
        
        try {
            // Validate input
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            // Check if username already exists
            if (userRepository.existsByUsername(request.getUsername())) {
                log.warn("Registration failed: Username already exists - {}", request.getUsername());
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            // Check if email already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                log.warn("Registration failed: Email already exists - {}", request.getEmail());
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // Create new user
            User user = new User();
            user.setUsername(request.getUsername().trim());
            user.setEmail(request.getEmail().trim());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("ROLE_USER");

            User savedUser = userRepository.save(user);
            log.info("User registered successfully: {} with ID: {}", savedUser.getUsername(), savedUser.getId());
            
            return ResponseEntity.ok("User registered successfully");
            
        } catch (Exception e) {
            log.error("Registration failed for username: {}", request.getUsername(), e);
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        log.info("Login attempt");
        
        try {
            // Extract username and password from Basic Auth header
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":", 2);
            String username = parts[0];
            String password = parts[1];

            log.info("Login attempt for user: {}", username);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Login successful for user: {}", username);
            
            return ResponseEntity.ok("Login successful");
            
        } catch (Exception e) {
            log.warn("Login failed: {}", e.getMessage());
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
            log.error("Error getting logged in user", e);
            return ResponseEntity.status(500).body("Error getting user: " + e.getMessage());
        }
    }
}