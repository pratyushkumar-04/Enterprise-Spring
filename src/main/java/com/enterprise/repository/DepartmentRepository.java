package com.enterprise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String>{

	boolean existsByName(String name);
	boolean existsById(String Id);
	
}
