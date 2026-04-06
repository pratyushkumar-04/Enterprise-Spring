package com.enterprise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.ReassignFaculty;
import com.enterprise.dto.request.SubjectFacultySectionAssignmentRequestDTO;
import com.enterprise.dto.response.SubjectFacultySectionAssignmentResponse;
import com.enterprise.service.SubjectFacultySectionAssignmentService;

@RestController
@RequestMapping("/faculty-assignment")
public class SubjectFacultyAssignmentController {

	@Autowired
	public SubjectFacultySectionAssignmentService assignmentService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createAssignment(
			@RequestBody SubjectFacultySectionAssignmentRequestDTO dto) {

		try{return ResponseEntity.ok(assignmentService.createAssignment(dto));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/section/{sectionId}/semester/{semester}")
	public ResponseEntity<List<SubjectFacultySectionAssignmentResponse>> getAssignments(@PathVariable String sectionId,
			@PathVariable Integer semester) {

		return ResponseEntity.ok(assignmentService.getAssignments(sectionId, semester));
	}
	
	@GetMapping("/faculty/{id}")
	public ResponseEntity<?> getAssignmentsByFaculty(@PathVariable String id){
		try {
			return ResponseEntity.ok(assignmentService.getAssignmentsByFacultyId(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/reassign")
	public ResponseEntity<SubjectFacultySectionAssignmentResponse> reassignFaculty(@RequestBody ReassignFaculty dto) {

		return ResponseEntity.ok(assignmentService.reassignFaculty(dto));
	}

	@DeleteMapping("/{assignmentId}")
	public ResponseEntity<String> deleteAssignment(@PathVariable String assignmentId) {

		assignmentService.deleteAssignment(assignmentId);

		return ResponseEntity.ok("Assignment deleted successfully");
	}
}
