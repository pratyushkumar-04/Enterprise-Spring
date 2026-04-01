package com.enterprise.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.LoginRequest;
import com.enterprise.entity.User;
import com.enterprise.security.CustomUserDetailService;
import com.enterprise.security.JwtService;
import com.enterprise.service.Userservie;

@RestController
@RequestMapping("/auth")
public class Authcontroller {
	
	@Autowired
	private Userservie userService;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailService customuserservice;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user)
	{
		try {
			userService.register(user);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("User registered Successfully");
		}
		catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
	}
	@CrossOrigin(origins = "*") 
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginReq)
	{
		try {
			authmanager.authenticate(
					new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
		}
		catch(AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
		}
		UserDetails userdetails = customuserservice.loadUserByUsername(loginReq.getUsername());
		String token = jwtService.generateToken(userdetails.getUsername());
		return ResponseEntity.ok(Map.of("token", token));
		}
	}
	
