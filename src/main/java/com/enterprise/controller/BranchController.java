package com.enterprise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.BranchRequest;
import com.enterprise.dto.response.BranchResponse;
import com.enterprise.service.BranchService;

@RestController
@RequestMapping("/branches")
public class BranchController {
	
	@Autowired
	private BranchService branchservice;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> addBranch(@RequestBody BranchRequest breq){
		try {
			BranchResponse br= branchservice.addbranch(breq);
			return ResponseEntity.ok(br);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/by-course/{courseId}")
	public List<BranchResponse> getBranchByCourse(@PathVariable String courseId){
		return branchservice.getBranchByCourseId(courseId);
	}
}
