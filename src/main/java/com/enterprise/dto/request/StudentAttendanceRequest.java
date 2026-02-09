package com.enterprise.dto.request;

import com.enterprise.enums.AttendanceStatus;

public class StudentAttendanceRequest {

	private String studentId;
	private AttendanceStatus status;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public AttendanceStatus getStatus() {
		return status;
	}
	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}
	
	
}
