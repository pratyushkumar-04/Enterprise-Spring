package com.enterprise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,String>{

	Faculty findTopByOrderByFacultyCodeDesc();
    List<Faculty> findByDepartmentId(String departmentId);
    List<Faculty> findByBranchId(String branchId);

}
