package com.enterprise.dto.response;

import java.time.LocalDate;

import com.enterprise.enums.AttendanceStatus;

public class AttendanceDateWiseResponse {
	private String sessionId;
	private LocalDate sessionDate;
	private AttendanceStatus status;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public LocalDate getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(LocalDate sessionDate) {
		this.sessionDate = sessionDate;
	}
	public AttendanceStatus getStatus() {
		return status;
	}
	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

}
