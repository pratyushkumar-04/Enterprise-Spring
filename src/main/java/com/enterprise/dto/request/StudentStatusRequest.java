package com.enterprise.dto.request;

import com.enterprise.enums.StudentStatus;

public class StudentStatusRequest {

	private StudentStatus  status;

	public StudentStatus getStatus() {
		return status;
	}

	public void setStatus(StudentStatus status) {
		this.status = status;
	}
	
	
}
