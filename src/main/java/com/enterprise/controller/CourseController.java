package com.enterprise.controller;

import java.util.List;

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

import com.enterprise.dto.request.CourseRequest;
import com.enterprise.dto.response.CourseResponse;
import com.enterprise.entity.Course;
import com.enterprise.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseservice;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> addCourse(@RequestBody CourseRequest c){
		try {
			CourseResponse cnew= courseservice.addCourse(c);
			return ResponseEntity.ok(cnew);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/by-deptId/{deptId}")
	public ResponseEntity<?> courseByDept(@PathVariable String deptId){
		try {
			List<Course> carr = courseservice.getCourseByDept(deptId);
			return ResponseEntity.ok(carr);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	// edit 
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/edit")
	public ResponseEntity<?> editCourse(@PathVariable String id,@RequestBody CourseRequest req){
		try {
			return ResponseEntity.ok(courseservice.editCourse(id, req));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	//view all courses
}
