package com.enterprise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enterprise.dto.Localuser;
import com.enterprise.entity.User;
import com.enterprise.repository.UserRepository;


// made for Authentication manager thing that helps in authentication 
@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    User us = userRepo.findByUsername(username)
	            .orElseThrow(() -> {
	                return new UsernameNotFoundException("Username Not Found");
	            });
		return new Localuser(us);
	}
}
