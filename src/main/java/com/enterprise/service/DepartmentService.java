package com.enterprise.service;

import java.util.List;
import java.util.Optional;

import com.enterprise.entity.Department;

public interface DepartmentService {

	Department addDepartment(Department dp);
	Optional <Department> getDepartment(String id);
	List<Department> fetchAllDepartments();
}
