package com.enterprise.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.enterprise.entity.User;


public interface Userservie {
	
	User register (User user);
	
	User authenticate(String username,String password);
	
	UserDetails findByusername(String username);
}
