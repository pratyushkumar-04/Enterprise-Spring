package com.enterprise.controller;

import java.net.ResponseCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.SectionRequest;
import com.enterprise.service.SectionService;

@RestController
@RequestMapping("/section")
public class SectionController {

	@Autowired 
	private SectionService sectionSer;
	
	@PostMapping
	private ResponseEntity<?> addSection(@RequestBody SectionRequest req){
		try {
			return ResponseEntity.ok(sectionSer.createSection(req));
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/branchsem")
	private ResponseEntity<?> getSections(@RequestParam String branchId,@RequestParam Integer semester){
		try {
			return ResponseEntity.ok(sectionSer.getSectionsByBranchSem(branchId, semester));
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
