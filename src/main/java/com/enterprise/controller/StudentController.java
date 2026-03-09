package com.enterprise.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

import com.enterprise.dto.request.RollNumrequest;
import com.enterprise.dto.request.SectionAssignRequest;
import com.enterprise.dto.request.StudentRequest;
import com.enterprise.dto.request.StudentStatusRequest;
import com.enterprise.dto.response.StudentResponse;
import com.enterprise.enums.StudentStatus;
import com.enterprise.service.StudentService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studService;

//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<?> addStudent(@RequestPart("student") StudentRequest sreq,
//			@RequestPart("image") MultipartFile image) {
//		try {
//			System.out.println("Incoming StudentRequest: " + sreq);
//			return ResponseEntity.status(HttpStatus.CREATED).body(studService.addStudent(sreq, image));
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		}
//	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addStudent(
			@RequestPart("student") StudentRequest sreq,
			@RequestPart(value = "image", required = false) MultipartFile image,
			@RequestPart(value = "adhaar", required = false) MultipartFile adhaar,
			@RequestPart(value = "tenth", required = false) MultipartFile tenth,
			@RequestPart(value = "twelth", required = false) MultipartFile twelth) {

		try {
			System.out.println("Endpoint Hit");
			System.out.println(sreq);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(studService.addStudent(sreq, image, adhaar, tenth, twelth));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/test")
	public String test() {
	    System.out.println("HIT TEST");
	    return "ok";
	}
	
	@PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
	@GetMapping
	public ResponseEntity<List<StudentResponse>> getAllStudents() {
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

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/status/{studentId}")
	public ResponseEntity<?> updateStatus(@PathVariable String studentId, @RequestBody StudentStatusRequest req) {
		try {
			return ResponseEntity.ok(studService.modifyStatus(studentId, req.getStatus()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/semester/{studentId}")
	private ResponseEntity<?> promotiom(@PathVariable String studentId) {
		try {
			return ResponseEntity.ok(studService.promotion(studentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/edit/{studentId}")
	public ResponseEntity<?> editStudent(@PathVariable String studentId, @RequestBody StudentRequest sreq) {
		try {
			return ResponseEntity.ok(studService.edit(studentId, sreq));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
	@PatchMapping("/{id}/roll-number")
	private ResponseEntity<?> assignRollNumber(@PathVariable String id, @RequestBody RollNumrequest req) {
		try {
			studService.assignRoll(id, req);
			return ResponseEntity.ok("Assigned Sucessfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	// Bulk Roll number Asssignment
	@PatchMapping("/sections/{sectionId}/generate-roll-numbers")
	public ResponseEntity<?> generateRollNumbers(@PathVariable String sectionId) {
	    studService.generateRollNumbersForSection(sectionId);
	    return ResponseEntity.ok("Roll numbers generated successfully");
	}
	

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/section")
	private ResponseEntity<?> assignSection(@PathVariable String id, @RequestBody SectionAssignRequest req) {
		try {
			studService.assignSection(id, req);
			return ResponseEntity.ok("Assigned Sucessfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
	@GetMapping("/section/{sectionId}")
	public ResponseEntity<List<StudentResponse>> getStudentsBySection(
	        @PathVariable String sectionId) {

	    List<StudentResponse> students = studService.getStudentsBySection(sectionId);

	    return ResponseEntity.ok(students);
	}
	
	@PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
	@GetMapping("/image/{id}")
	public ResponseEntity<Resource> getImage(@PathVariable String id) throws IOException,MalformedURLException{

	    StudentResponse student = studService.getStudentById(id);

	    Path path = Paths.get(student.getProfileImagePath());
	    Resource resource = new UrlResource(path.toUri());

	    return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(resource);
	}
	
	
	@PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
	@GetMapping("/document/{id}/{type}")
	public ResponseEntity<Resource> getDocument(
	        @PathVariable String id,
	        @PathVariable String type) throws IOException,MalformedURLException{

	    StudentResponse student = studService.getStudentById(id);

	    String filePath = switch (type) {
	        case "adhaar" -> student.getAdhaarpath();
	        case "tenth" -> student.getTenthMarksheet();
	        case "twelth" -> student.getTwelthMarksheet();
	        default -> throw new RuntimeException("Invalid document type");
	    };

	    Path path = Paths.get(filePath);
	    Resource resource = new UrlResource(path.toUri());

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_PDF)
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "inline; filename=" + resource.getFilename())
	            .body(resource);
	}
}
