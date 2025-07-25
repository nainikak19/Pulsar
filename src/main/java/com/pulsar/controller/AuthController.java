package com.pulsar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsar.dto.RegisterRequest;
import com.pulsar.model.User;
import com.pulsar.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
// /api/auth/login
    //@PostMapping("/login")
	/*
	 * public ResponseEntity<User> login(@RequestBody LoginRequest request) { return
	 * ResponseEntity.ok(authService.login(request)); }
	 */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @GetMapping("/login")
	public ResponseEntity<User> signInHandler(Authentication auth) throws BadCredentialsException, Exception{
		
		User customer= authService.findByEmail(auth.getName());
		
		if(customer!=null)
		{
			 return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
		}
		
		throw new BadCredentialsException("Invalid Username or password");
		

	}
	
	// Authentication with JWT token 
	
	@GetMapping("/app/logged-in/user")
	public ResponseEntity<String> welcomeLoggedInUserHandler() throws Exception
	{
		User user =  authService.login();
		
		String message = "Hello from GreenStitch";
		
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
}

