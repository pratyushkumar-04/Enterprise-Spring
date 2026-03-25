package com.enterprise.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.enterprise.dto.request.FacultyModifyRequest;
import com.enterprise.dto.request.FacultyRequest;
import com.enterprise.dto.request.FacultySubjectAssignRequest;
import com.enterprise.dto.response.FacultyResponse;
import com.enterprise.enums.FacultyStatus;
import com.enterprise.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

	@Autowired
	private FacultyService facultyService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addFaculty( @RequestPart("faculty") FacultyRequest request,
	        @RequestPart(value = "image", required = false) MultipartFile image,
	        @RequestPart(value = "cv", required = false) MultipartFile cv){
		try {
			return ResponseEntity.ok(facultyService.addFaculty(request, image, cv));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<FacultyResponse>> getAllFaculties(){	
		return ResponseEntity.ok(facultyService.getAllFaculties());
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<?> getFacultyById(@PathVariable String Id){
		try {
			return ResponseEntity.ok(facultyService.getFacultyById(Id));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/department/{deptId}")
	public ResponseEntity<?> getFacultiesByDept(@PathVariable String deptId){
		try {
			return ResponseEntity.ok(facultyService.getFacultyByDept(deptId));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping("/status/{Id}")
	public ResponseEntity<?> changeStatus(@PathVariable String Id,
			@RequestBody Map<String, FacultyStatus> body){
		try {
			FacultyStatus status = body.get("status");
			return ResponseEntity.ok(facultyService.changeStatus(Id, status));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/assign-subject")
	public ResponseEntity<?> assignSubject(@RequestBody FacultySubjectAssignRequest subjectReq){
		try {
			return ResponseEntity.ok(facultyService.assignSubject(subjectReq));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@PutMapping("/{Id}")
	public ResponseEntity<?> editFaculty(@PathVariable String Id,@RequestBody FacultyModifyRequest req ){
		try {
			return ResponseEntity.ok(facultyService.editFaculty(Id, req));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/image/{Id}")
	public ResponseEntity<?> getImage(@PathVariable String Id){

		try {
			Resource res = facultyService.getImage(Id);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/{Id}/cv")
	public ResponseEntity<?> getCv(@PathVariable String Id){
		try {
			Resource res= facultyService.getCv(Id);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + res.getFilename()).body(res);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
