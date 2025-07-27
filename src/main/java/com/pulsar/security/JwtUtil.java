package com.pulsar.security;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {



	/*
	 * //@Value("${jwt.secret}") private String secret;
	 * 
	 * private SecretKey key;
	 * 
	 * @PostConstruct public void initKey() { this.key =
	 * Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)); }
	 * 
	 * public String extractUsername(String token) { return extractClaim(token,
	 * Claims::getSubject); }
	 * 
	 * public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	 * final Claims claims = extractAllClaims(token); return
	 * claimsResolver.apply(claims); }
	 * 
	 * private Claims extractAllClaims(String token) { return Jwts .parserBuilder()
	 * .setSigningKey(key) .build() .parseClaimsJws(token) .getBody(); }
	 * 
	 * public String generateToken(String username) { return Jwts .builder()
	 * .setSubject(username) .setIssuedAt(new Date(System.currentTimeMillis()))
	 * .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //
	 * 10 hours .signWith(key, SignatureAlgorithm.HS256) .compact(); }
	 * 
	 * public boolean isTokenValid(String token, UserDetails userDetails) { final
	 * String extractedUsername = extractUsername(token); return
	 * (extractedUsername.equals(userDetails.getUsername()) &&
	 * !isTokenExpired(token)); }
	 * 
	 * private boolean isTokenExpired(String token) { return extractClaim(token,
	 * Claims::getExpiration).before(new Date()); }
	 */
}
