package com.enterprise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enterprise.dto.request.RollNumrequest;
import com.enterprise.dto.request.SectionAssignRequest;
import com.enterprise.dto.request.StudentRequest;
import com.enterprise.dto.response.StudentResponse;
import com.enterprise.enums.StudentStatus;
import com.enterprise.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private ResponseEntity<?> addStudent(@RequestPart("student") StudentRequest sreq,
			@RequestPart("image") MultipartFile image) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(studService.addStudent(sreq, image));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping
	private ResponseEntity<List<StudentResponse>> getAllStudents() {
		return ResponseEntity.ok(studService.getAllstudents());
	}

	@GetMapping("/{Id}")
	private ResponseEntity<?> getStudentById(@PathVariable String Id) {
		try {
			return ResponseEntity.ok(studService.getStudentById(Id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/admission/{admNo}")
	private ResponseEntity<?> getStudentByadmNo(@PathVariable String admNo) {
		try {
			return ResponseEntity.ok(studService.getStudentByAdmnum(admNo));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/branch/{branchId}")
	private ResponseEntity<?> getStudentByBranchId(@PathVariable String branchId) {
		try {
			return ResponseEntity.ok(studService.getStudentsByBranch(branchId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/semester/{sem}")
	private ResponseEntity<?> getStudentBysemester(@PathVariable Integer sem) {
		try {
			return ResponseEntity.ok(studService.getStudentBySem(sem));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/status/{status}")
	private ResponseEntity<?> getStudentByStatus(@PathVariable StudentStatus status) {
		try {
			return ResponseEntity.ok(studService.getStudentByStatus(status));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PatchMapping("/status/{studentId}")
	private ResponseEntity<?> updateStatus(@PathVariable String studentId, @RequestBody StudentStatus status) {
		try {
			return ResponseEntity.ok(studService.modifyStatus(studentId, status));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PatchMapping("/semester/{studentId}")
	private ResponseEntity<?> promotiom(@PathVariable String studentId) {
		try {
			return ResponseEntity.ok(studService.promotion(studentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/edit/{studentId}")
	private ResponseEntity<?> editStudent(@PathVariable String studentId, @RequestBody StudentRequest sreq) {
		try {
			return ResponseEntity.ok(studService.edit(studentId, sreq));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PatchMapping("/{id}/roll-number")
	private ResponseEntity<?> assignRollNumber(@PathVariable String id, @RequestBody RollNumrequest req) {
		try {
			studService.assignRoll(id, req);
			return ResponseEntity.ok("Assigned Sucessfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}/section")
	private ResponseEntity<?> assignSection(@PathVariable String id,@RequestBody SectionAssignRequest req){
		try {
			studService.assignSection(id, req);
			return ResponseEntity.ok("Assigned Sucessfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
