package com.enterprise.dto.response;

import com.enterprise.enums.AttendanceStatus;

public class FacultyAttendanceResponse {

	private String StudentName;
	private AttendanceStatus status;
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	public AttendanceStatus getStatus() {
		return status;
	}
	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}
	
	
}
