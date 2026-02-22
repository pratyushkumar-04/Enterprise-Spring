package com.enterprise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.SubjectChangeRequest;
import com.enterprise.dto.request.SubjectRequest;
import com.enterprise.dto.request.SubjectStatusRequest;
import com.enterprise.dto.response.SubjectResponse;
import com.enterprise.service.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> addSubject(@RequestBody SubjectRequest subReq) {
		try {
			SubjectResponse resp = subService.addSubject(subReq);
			return ResponseEntity.status(HttpStatus.CREATED).body(resp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/{Id}")
	public ResponseEntity<?> getSubjecvtById(@PathVariable String Id) {
		try {
			return ResponseEntity.ok(subService.getSubjectById(Id));
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/by-branch/{branchId}/semester/{semester}")
	public ResponseEntity<?> getSubByBranchSem(@PathVariable String branchId, @PathVariable Integer semester) {
		try {
			return ResponseEntity.ok(subService.getSubjectByBranchSem(branchId, semester));
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{subId}")
	public ResponseEntity<?> updateSubject(@RequestBody SubjectChangeRequest subchange, @PathVariable String subId) {
		try {
			return ResponseEntity.ok(subService.editSubject(subId, subchange));
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("status/{subId}")
	public ResponseEntity<?>changeSubjectStatus(@RequestBody SubjectStatusRequest newstat,@PathVariable String subId){
		try {
			return ResponseEntity.ok(subService.modifyStatus(subId, newstat));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	//get subjects by branch sem 

}
