package com.enterprise.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enterprise.dto.Localuser;
import com.enterprise.entity.User;
import com.enterprise.repository.UserRepository;
import com.enterprise.service.Userservie;

@Service
public class UserServiceImpl implements Userservie {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public User register(User user) {
		if (userRepo.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Username Aleardy in use");

		}
		String encodedpassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedpassword);
		return userRepo.save(user);
	}

	@Override
	public User authenticate(String username, String rawpassword) {

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Username or password"));

		if (!passwordEncoder.matches(rawpassword, user.getPassword())) {
			throw new IllegalArgumentException("Invalid Credentials");
		}
		return user;
	}
	
	
	@Override
	public UserDetails findByusername(String username) throws UsernameNotFoundException {

	    User user = userRepo.findByUsername(username)
	            .orElseThrow(() -> {
	            	return new UsernameNotFoundException("Username Not Found");
	            	});
	    return new Localuser(user);
	    
	}
}
