package com.enterprise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, String>{

}
