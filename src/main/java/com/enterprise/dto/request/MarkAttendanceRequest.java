package com.enterprise.dto.request;

import java.util.List;

public class MarkAttendanceRequest {

	private String 	sessionId;
	private List<StudentAttendanceRequest> attendanceList;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public List<StudentAttendanceRequest> getAttendanceList() {
		return attendanceList;
	}
	public void setAttendanceList(List<StudentAttendanceRequest> attendanceList) {
		this.attendanceList = attendanceList;
	}
	
	
}
