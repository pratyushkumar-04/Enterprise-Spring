package com.enterprise.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import com.enterprise.dto.request.AttendanceSessionRequest;
import com.enterprise.dto.request.MarkAttendanceRequest;
import com.enterprise.dto.response.AttedanceSessionResponse;
import com.enterprise.dto.response.AttendanceStudentResponse;
import com.enterprise.dto.response.AttendanceSubjectWiseResponse;
import com.enterprise.dto.response.CurrentClassResponse;
import com.enterprise.dto.response.SubjectAttendanceDetailResponse;

public interface AttendanceService {

	AttedanceSessionResponse createSession(AttendanceSessionRequest req);
	void markAttendance(MarkAttendanceRequest req);
	List<AttendanceSubjectWiseResponse> getSubjectWiseAttendance(String studentId);
    List<AttendanceStudentResponse> getStudentsForAttendance(String sessionId);
    SubjectAttendanceDetailResponse getSubjectAttendanceDetail(String studentId,String subjectId);
    CurrentClassResponse getCurrentClass(String facultyId);
    CurrentClassResponse getCurrentClassWithClock(String facultyId,Clock fixedClock);
    List<CurrentClassResponse> getFacultyClassesForAttendance(
            String facultyId,
            LocalDate date);
    
    List<CurrentClassResponse> getSessionsByFacuty(String facultyId);
}
