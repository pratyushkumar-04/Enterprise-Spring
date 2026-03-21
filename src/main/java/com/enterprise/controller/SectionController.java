package com.enterprise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.SectionModifyReq;
import com.enterprise.dto.request.SectionRequest;
import com.enterprise.service.SectionService;

@RestController
@RequestMapping("/section")
public class SectionController {

	@Autowired
	private SectionService sectionSer;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> addSection(@RequestBody SectionRequest req) {
		try {
			return ResponseEntity.ok(sectionSer.createSection(req));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAllSections(){
		try {
			return ResponseEntity.ok(sectionSer.getAllSections());
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/branchsem")
	public ResponseEntity<?> getSections(@RequestParam String branchId, @RequestParam Integer semester) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			System.out.println("Authorities: " + auth.getAuthorities());
			return ResponseEntity.ok(sectionSer.getSectionsByBranchSem(branchId, semester));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/edit")
	public ResponseEntity<?> modifySection(@PathVariable String id,@RequestBody SectionModifyReq req ){
		try {
			return ResponseEntity.ok(sectionSer.modifySection(id, req));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
