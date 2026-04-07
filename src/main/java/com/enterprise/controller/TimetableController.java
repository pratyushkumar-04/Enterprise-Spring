package com.enterprise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.TimetableRequest;
import com.enterprise.dto.response.TimetableResponse;
import com.enterprise.service.TimetableService;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

	@Autowired
	private TimetableService timetableService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
    public ResponseEntity<?> createTimetableEntry(
            @RequestBody TimetableRequest request) {

        try {
            return ResponseEntity.ok(
                    timetableService.createTimetableEntry(request)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<TimetableResponse>> getSectionTimetable(
            @PathVariable String sectionId) {

        return ResponseEntity.ok(
                timetableService.getSectionTimetable(sectionId)
        );
    }

	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimetableEntry(@PathVariable String id) {

        try {
            timetableService.deleteTimetableEntry(id);
            return ResponseEntity.ok("Timetable entry deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	@PreAuthorize("hasRole('FACULTY')")
	@GetMapping("/faculty/{facultyId}")
	 public ResponseEntity<List<TimetableResponse>> getFacultyTimetable(
	            @PathVariable String facultyId) {

	 return ResponseEntity.ok(
	                timetableService.getFacultyTimetable(facultyId)
	        );
	}
}
