package com.enterprise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	boolean existsByUsername(String Username);
	Optional<User> findByUsername(String Username);
}
