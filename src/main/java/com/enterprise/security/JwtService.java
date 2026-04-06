package com.enterprise.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.enterprise.entity.User;
import com.enterprise.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	
	//responsible for generating and validating tokens

	
	@Value("${secretkey}")
	private String secret;
	
	private final long jwtExpirationMs = 7200000;
	
	@Autowired
	private UserRepository userRepo;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		
		Optional<User> userOpt = userRepo.findByUsername(username);
		if (userOpt.isPresent()) {
			User user= userOpt.get();
			String id = user.getRefId();
			claims.put("role", user.getRole().name());
			claims.put("id", id);
			
		}
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
		}
	
	public boolean validateToken(String token, UserDetails userDetails) {
	    try {
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

	        String username = claims.getSubject();
	        Date expiration = claims.getExpiration();

	        return username != null
	                && username.equals(userDetails.getUsername())
	                && expiration != null
	                && expiration.after(new Date());
	    } catch (JwtException | IllegalArgumentException e) {
	        return false;
	    }
	}
		
		public String extractusername(String token) {
			return Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
				}
}
