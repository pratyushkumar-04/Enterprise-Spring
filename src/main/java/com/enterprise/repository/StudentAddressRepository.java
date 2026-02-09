package com.enterprise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.StudentAddress;

public interface StudentAddressRepository extends JpaRepository<StudentAddress, String>{

}
