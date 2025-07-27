package com.pulsar.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pulsar.dto.RegisterRequest;
import com.pulsar.model.User;
import com.pulsar.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	// private final JwtUtil jwtUtil;
	// private final AuthenticationManager authenticationManager;

	public User register(RegisterRequest request) {
		User user = User.builder().email(request.getEmail()).username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).role("ROLE_USER").build();

		return userRepository.save(user);

		// return new AuthResponse(token, "Registered successfully");
	}

	public User login() {
		SecurityContext sc = SecurityContextHolder.getContext();

		Authentication auth = sc.getAuthentication();

		String userName = auth.getName();

		User user = userRepository.findByEmail(userName).orElse(null);

		return user;
	}
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
}
