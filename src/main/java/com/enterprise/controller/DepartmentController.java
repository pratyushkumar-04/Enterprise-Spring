package com.enterprise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.DepartmentReq;
import com.enterprise.entity.Department;
import com.enterprise.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService deptService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> addDepartment(@RequestBody Department dp){
		System.out.print(dp.getName());
		try {
		Department ndp = deptService.addDepartment(dp);
		return ResponseEntity.ok(ndp);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<Department> getAllDepartments(){
		return deptService.fetchAllDepartments();
	}
	
	@GetMapping("/{id}")
	public Optional<Department> getSpecificDept(@PathVariable String id)
	{
		return deptService.getDepartment(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/edit")
	public ResponseEntity<?> editDepartment(@PathVariable String id,
			@RequestBody DepartmentReq req){
		try {
			return ResponseEntity.ok(deptService.editDepartment(id, req));
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	
	}
	

}
