package com.enterprise.controller;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.dto.request.AttendanceSessionRequest;
import com.enterprise.dto.request.MarkAttendanceRequest;
import com.enterprise.dto.response.AttendanceOverviewResponse;
import com.enterprise.dto.response.AttendanceStudentResponse;
import com.enterprise.dto.response.FacultySessions;
import com.enterprise.dto.response.SubjectAttendanceDetailResponse;
import com.enterprise.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService sessionService;

	// Create attendance Sessions (Faculty does this when he/she clicks over any
	// day to mark attendance this endpoint will be called )

	@PreAuthorize("hasRole('FACULTY')")
	@PostMapping("/session")
	public ResponseEntity<?> createSession(@RequestBody AttendanceSessionRequest req) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.createSession(req));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// displays list of students of that section in ascending order that will help
	// teacher
	// to mark attendance by roll number
	// also used when a marked session is opened by a faculty to edit or just see
	// which students are marked or not
	// will be helpful while editing attendance

	@PreAuthorize("hasRole('FACULTY')")
	@GetMapping("{sessionId}/students")
	public ResponseEntity<?> getStudentsForAttendance(@PathVariable String sessionId) {

		try {
			return ResponseEntity.ok(sessionService.getStudentsForAttendance(sessionId));

		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}

	// faculty role
	// marks attendance from the ui over the list displayed from above endpoint and
	// here the studebt id will be used to mark
	// present or absent

	@PreAuthorize("hasRole('FACULTY')")
	@PostMapping("/mark")
	public ResponseEntity<?> markAttendance(@RequestBody MarkAttendanceRequest req) {
		try {
			sessionService.markAttendance(req);
			return ResponseEntity.status(HttpStatus.CREATED).body("Marked");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	// Veiws for Students

	// Currently sending StudentId as parameter for development will use JWT id
	// further

	// enter student id and you will get list of all subjects you are enrolled to
	// with
	// attendance details in each subject present, absent, Percentage

	@PreAuthorize("hasAnyRole('FACULTY','ADMIN','STUDENT')")
	@GetMapping("/subject-wise/{studentId}")
	public ResponseEntity<?> getSubjectSummary(@PathVariable String studentId) {
		try {
			return ResponseEntity.ok(sessionService.getSubjectWiseAttendance(studentId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// used to get attendance of a particular student of a particular subject
	// will be used when we want to check detailed response over a particular
	// subject
	// regarding which day i was present or absent

	@PreAuthorize("hasAnyRole('FACULTY','ADMIN','STUDENT')")
	@GetMapping("/subject/{subjectId}/detail/{studentId}")
	public ResponseEntity<SubjectAttendanceDetailResponse> getSubjectDetail(@PathVariable String subjectId,
			@PathVariable String studentId) {

		return ResponseEntity.ok(sessionService.getSubjectAttendanceDetail(studentId, subjectId));
	}

	@PreAuthorize("hasRole('FACULTY')")
	@GetMapping("/current-class/{facultyId}")
	public ResponseEntity<?> getCurrentClass(@PathVariable String facultyId,
			@RequestHeader(value = "X-Debug-Time", required = false) String debugTime) {

		try {

			if (debugTime != null) {
				LocalDateTime dt = LocalDateTime.parse(debugTime);

				// override clock dynamically
				Clock fixedClock = Clock.fixed(dt.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

				return ResponseEntity.ok(sessionService.getCurrentClassWithClock(facultyId, fixedClock));
			}

			return ResponseEntity.ok(sessionService.getCurrentClass(facultyId));

		} catch (Exception e) {
			return ResponseEntity.ok(Map.of("hasCurrentClass", false));
		}
	}

	@PreAuthorize("hasRole('FACULTY')")
	@GetMapping("/faculty/{facultyId}/classes")
	public ResponseEntity<?> getFacultyClassesForAttendance(@PathVariable String facultyId,
			@RequestParam LocalDate date) {

		return ResponseEntity.ok(sessionService.getFacultyClassesForAttendance(facultyId, date));
	}

	@PreAuthorize("hasRole('FACULTY')")
	@GetMapping("/faculty/{facultyId}/sessions")
	public ResponseEntity<List<FacultySessions>> getFacultySessions(@PathVariable String facultyId,
			@RequestParam(required = false) LocalDate fromDate, @RequestParam(required = false) LocalDate toDate) {

		return ResponseEntity.ok(sessionService.getFacultySessions(facultyId, fromDate, toDate));
	}

	@PreAuthorize("hasRole('FACULTY')")
	@GetMapping("/faculty/{facultyId}/subject/{subjectId}/section/{sectionId}/summary")
	public ResponseEntity<AttendanceOverviewResponse> getSummary(@PathVariable String facultyId,
			@PathVariable String subjectId, @PathVariable String sectionId) {
		return ResponseEntity.ok(sessionService.getStudentAttendanceSummary(subjectId, sectionId));
	}

}
