package com.pulsar.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import com.pulsar.config.jwt.JwtGeneratorFilter;
import com.pulsar.config.jwt.JwtValidationFilter;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain mySecurityConfig(HttpSecurity http) throws Exception {
		
		http.sessionManagement(sessionManagement -> 
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		// CORS configuration
		.cors(cors -> {
			cors.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration cfg = new CorsConfiguration();
					cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.setAllowCredentials(true);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(Arrays.asList("Authorization"));
					return cfg;
				}
			});
		})
		
		// Authorization rules - ORDER MATTERS!
		.authorizeHttpRequests(auth -> auth
			// Public endpoints first
			.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
			//.requestMatchers(HttpMethod.GET, "/api/auth/login").permitAll()
			.requestMatchers("/v3/api-docs/**", "/swagger-ui*/**").permitAll()
			//.requestMatchers("/error").permitAll()  // Allow error endpoint
			
			// Protected endpoints
			.requestMatchers("/**").hasAnyRole("USER", "ADMIN")
			
			// All other requests require authentication
			.anyRequest().authenticated()
		)
		
		// Disable CSRF for REST API (recommended for stateless JWT-based APIs)
		.csrf(csrf -> csrf.disable())
		
		// Add JWT filters
		.addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtValidationFilter(), BasicAuthenticationFilter.class)
		
		// Disable form login and basic auth for REST API
		.httpBasic(basic -> basic.disable())
		.formLogin(form -> form.disable());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}